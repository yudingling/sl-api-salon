package com.sl.api.salon.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.sl.api.salon.mapper.SlMsgMapper;
import com.sl.api.salon.model.SToken;
import com.sl.api.salon.model.db.SlMsg;

@Service
public class MsgService {
	@Autowired
	private SlMsgMapper msgMapper;
	
	public List<SlMsg> getMsgs(SToken token, boolean readed, int startIndex, int size){
		return this.msgMapper.getMsgs(token.getUserId(), readed ? 1 : 0, startIndex, size);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void readMsgs(SToken token, Set<Long> msgIds){
		Example example = new Example(SlMsg.class);
	    example.createCriteria().andIn("msgId", msgIds).andEqualTo("uId", token.getUserId()).andEqualTo("msgReaded", 0);
		
	    SlMsg upt = new SlMsg();
	    upt.setUptTs(System.currentTimeMillis());
	    upt.setMsgReaded(1);
	    
		this.msgMapper.updateByExampleSelective(upt, example);
	}
}
