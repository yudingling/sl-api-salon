package com.sl.api.salon.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.filter.FilterHttpServletRequest;
import com.sl.api.salon.model.db.SlMsg;
import com.sl.api.salon.service.MsgService;
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
	public ApiResult update(@RequestParam(name = "msgIds[]") Set<Long> msgIds, FilterHttpServletRequest request){
		Assert.notEmpty(msgIds, "msgIds should not be null or empty");
		
		this.msgService.readMsgs(request.getToken(), msgIds);
		
		return ApiResult.success();
	}
}
