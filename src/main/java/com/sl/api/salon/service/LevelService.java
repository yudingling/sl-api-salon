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
import com.sl.api.salon.model.PayType;
import com.sl.api.salon.model.SToken;
import com.sl.api.salon.model.db.SlLevel;
import com.sl.api.salon.model.db.SlUserLevel;
import com.sl.api.salon.model.db.SlUserLevelOrder;
import com.zeasn.common.daemon.Daemon;
import com.zeasn.common.daemon.IWriteBack;
import com.zeasn.common.feign.api.SnowFlakeApi;
import com.zeasn.common.util.DateUtil;

@Service
public class LevelService implements IWriteBack<SlUserLevelOrder> {
	@Autowired
	private SlLevelMapper slLevelMapper;
	@Autowired
	private SlUserLevelOrderMapper slUserLevelOrderMapper;
	@Autowired
	private SlUserLevelMapper slUserLevelMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	@Autowired
	private Daemon daemon;
	
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
	
	public boolean levelUpPaied(SToken token, Long ulodId){
		SlUserLevelOrder order = this.getLevelUpOrder(token, ulodId);
		if(order != null){
			if(order.getUlodPaiedTs() == null){
				//level up
				this.daemon.protect(this, order);
			}
			
			return true;
			
		}else{
			return false;
		}
	}
	
	private SlLevel getLevel(SToken token, Long levelId){
		Example example = new Example(SlLevel.class);
	    example.createCriteria().andEqualTo("bdId", token.getBrandId()).andEqualTo("levelEnable", 1).andEqualTo("levelId", levelId);
	    
	    List<SlLevel> levels = this.slLevelMapper.selectByExample(example);
	    return CollectionUtils.isNotEmpty(levels) ? levels.get(0) : null;
	}
	
	private SlUserLevelOrder getLevelUpOrder(SToken token, Long ulodId){
		Example example = new Example(SlUserLevelOrder.class);
	    example.createCriteria().andEqualTo("ulodId", ulodId).andEqualTo("uId", token.getUserId());
	    
	    List<SlUserLevelOrder> orders = this.slUserLevelOrderMapper.selectByExample(example);
	    return CollectionUtils.isNotEmpty(orders) ? orders.get(0) : null;
	}
	
	private void levelUp(SlUserLevelOrder order){
		Long ts = System.currentTimeMillis();
		
		SlUserLevelOrder uptOrder = new SlUserLevelOrder();
		uptOrder.setUlodId(order.getUlodId());
		uptOrder.setUlodPaiedTs(ts);
		uptOrder.setUlodPaiedTp(PayType.WECHAT.toString());
		
		this.slUserLevelOrderMapper.updateByPrimaryKeySelective(uptOrder);
		
		SlUserLevel obj = new SlUserLevel(
				this.snowFlakeApi.nextId(), 
				order.getuId(), 
				order.getLevelId(), 
				order.getUlodStm(),
				order.getUlodEtm(),
				order.getUlodDiscount(),
				1, ts, ts);
		
		this.slUserLevelMapper.insert(obj);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean writeBack(SlUserLevelOrder data) {
		this.levelUp(data);
		return true;
	}
}
