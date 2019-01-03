package com.sl.api.salon.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.sl.api.salon.mapper.SlBarberWorkLikeMapper;
import com.sl.api.salon.mapper.SlBarberWorkMapper;
import com.sl.api.salon.model.SToken;
import com.sl.api.salon.model.db.SlBarberWork;
import com.sl.api.salon.model.db.SlBarberWorkLike;
import com.zeasn.common.feign.api.SnowFlakeApi;

@Service
public class BarberWorkService {
	@Autowired
	private SlBarberWorkMapper workMapper;
	@Autowired
	private SlBarberWorkLikeMapper workLikeMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	
	public List<SlBarberWork> getWorks(SToken token, Long shopId, int startIndex, int size, int orderBy){
		if(orderBy == 0){
			return this.workMapper.getWorksByTime(token.getBrandId(), shopId, startIndex, size);
			
		}else{
			return this.workMapper.getWorksByLike(token.getBrandId(), shopId, startIndex, size);
		}
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
