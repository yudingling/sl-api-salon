package com.sl.api.salon.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.sl.api.salon.mapper.SlBarberWorkLikeMapper;
import com.sl.api.salon.mapper.SlBarberWorkMapper;
import com.sl.api.salon.model.BarberWork;
import com.sl.api.salon.model.BarberWorkExt;
import com.sl.common.model.SToken;
import com.sl.common.model.db.SlBarberWorkLike;
import com.zeasn.common.feign.api.SnowFlakeApi;

@Service
public class BarberWorkService {
	@Autowired
	private SlBarberWorkMapper workMapper;
	@Autowired
	private SlBarberWorkLikeMapper workLikeMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	@Autowired
	private CommonService commonService;
	
	public List<BarberWork> getWorks(SToken token, Long shopId, int startIndex, int size, int orderBy){
		List<BarberWorkExt> bws = null; 
		if(orderBy == 0){
			bws = this.workMapper.getWorksByTime(token.getBrandId(), shopId, startIndex, size);
			
		}else{
			bws = this.workMapper.getWorksByLike(token.getBrandId(), shopId, startIndex, size);
		}
		
		List<BarberWork> result = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(bws)){
			for(BarberWorkExt item : bws){
				result.add(new BarberWork(item, this.commonService.getIconUrl(item)));
			}
		}
		
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void likeWork(SToken token, Long workId){
		Long ts = System.currentTimeMillis();
		
		SlBarberWorkLike bl = new SlBarberWorkLike(this.snowFlakeApi.nextId(), workId, token.getUserId(), ts, ts);
		if(this.workLikeMapper.insert(bl) == 1){
			this.workMapper.likeWork(workId);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void dislikeWork(SToken token, Long workId){
		Example example = new Example(SlBarberWorkLike.class);
	    example.createCriteria().andEqualTo("bbwId", workId).andEqualTo("uId", token.getUserId());
		
		if(this.workLikeMapper.deleteByExample(example) == 1){
			this.workMapper.dislikeWork(workId);
		}
	}
}
