package com.sl.api.salon.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.sl.api.salon.mapper.SlOrderEvaluationMapper;
import com.sl.api.salon.mapper.SlOrderMapper;
import com.sl.common.model.SToken;
import com.sl.common.model.db.SlOrder;
import com.sl.common.model.db.SlOrderEvaluation;
import com.zeasn.common.feign.api.SnowFlakeApi;

@Service
public class EvaluationService {
	@Autowired
	private SlOrderMapper slOrderMapper;
	@Autowired
	private SlOrderEvaluationMapper slOrderEvaluationMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	
	public boolean createEvaluation(SToken token, Long odId, Integer evaVal, String evaDesc){
		SlOrder order = this.getOrder(token, odId);
		
		if(order == null){
			return false;
			
		}else{
			Long ts = System.currentTimeMillis();
			
			SlOrderEvaluation eva = new SlOrderEvaluation(
					this.snowFlakeApi.nextId(), order.getOdId(), evaVal, evaDesc, ts, ts);
			
			return this.slOrderEvaluationMapper.insert(eva) == 1;
		}
	}
	
	private SlOrder getOrder(SToken token, Long odId){
		Example example = new Example(SlOrder.class);
	    example.createCriteria().andEqualTo("odId", odId).andEqualTo("odUid", token.getUserId());
	    
	    List<SlOrder> data = this.slOrderMapper.selectByExample(example);
	    return CollectionUtils.isNotEmpty(data) ? data.get(0) : null;
	}
}
