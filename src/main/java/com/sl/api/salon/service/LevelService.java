package com.sl.api.salon.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.sl.api.salon.mapper.SlLevelMapper;
import com.sl.api.salon.mapper.SlUserLevelMapper;
import com.sl.api.salon.mapper.SlUserLevelOrderMapper;
import com.sl.common.model.SToken;
import com.sl.common.model.db.SlLevel;
import com.sl.common.model.db.SlUserLevelOrder;
import com.zeasn.common.feign.api.SnowFlakeApi;
import com.zeasn.common.util.DateUtil;

@Service
public class LevelService {
	@Autowired
	private SlLevelMapper slLevelMapper;
	@Autowired
	private SlUserLevelOrderMapper slUserLevelOrderMapper;
	@Autowired
	private SlUserLevelMapper slUserLevelMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	
	public List<SlLevel> getLevels(SToken token){
		Example example = new Example(SlLevel.class);
	    example.createCriteria().andEqualTo("bdId", token.getBrandId()).andEqualTo("levelEnable", 1);
	    example.orderBy("levelPrice").asc();
	    
	    return this.slLevelMapper.selectByExample(example);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public SlUserLevelOrder createLevelUpOrder(SToken token, Long levelId){
		Long ulodId = this.snowFlakeApi.nextId();
		Long ulodStm = System.currentTimeMillis();
		
		SlLevel level = this.getLevel(token, levelId);
		if(level != null){
			Long ulodEtm = DateUtil.addMonth(ulodStm, level.getLevelMonth());
			
			SlUserLevelOrder order = new SlUserLevelOrder(ulodId, token.getUserId(), levelId, ulodStm, ulodEtm, 
					level.duration(), level.getLevelDiscount(), level.getLevelPrice(), null, null, ulodStm, ulodStm);
			
			if(this.slUserLevelOrderMapper.insert(order) == 1){
				return order;
			}
		}
		
		return null;
	}
	
	private SlLevel getLevel(SToken token, Long levelId){
		Example example = new Example(SlLevel.class);
	    example.createCriteria().andEqualTo("bdId", token.getBrandId()).andEqualTo("levelEnable", 1).andEqualTo("levelId", levelId);
	    
	    List<SlLevel> levels = this.slLevelMapper.selectByExample(example);
	    return CollectionUtils.isNotEmpty(levels) ? levels.get(0) : null;
	}
}
