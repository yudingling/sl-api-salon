package com.sl.api.salon.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.sl.api.salon.service.MsgService;
import com.sl.common.filter.FilterHttpServletRequest;
import com.sl.common.model.db.SlMsg;
import com.zeasn.common.model.result.ApiArrayResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/msg")
public class MsgController {
	@Autowired
	private MsgService msgService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(@RequestParam Boolean readed, @RequestParam Integer startIndex, @RequestParam Integer size, FilterHttpServletRequest request){
		Assert.notNull(readed, "readed should not be null or empty");
		Assert.notNull(startIndex, "startIndex should not be null or empty");
		Assert.notNull(size, "size should not be null or empty");
		
		List<SlMsg> msgs = this.msgService.getMsgs(request.getToken(), readed, startIndex, size);
		
		return new ApiArrayResult<>(msgs);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ApiResult update(@RequestParam String msgIds, FilterHttpServletRequest request){
		Assert.notNull(msgIds, "msgIds should not be null or empty");
		
		Set<Long> ids = new HashSet<>(JSON.parseArray(msgIds, Long.class));
		Assert.notEmpty(ids, "msgIds should not be null or empty");
		
		this.msgService.readMsgs(request.getToken(), ids);
		
		return ApiResult.success();
	}
}
