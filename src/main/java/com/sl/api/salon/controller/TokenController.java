package com.sl.api.salon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.service.TokenService;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/token")
public class TokenController {
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(@RequestParam String wechatUnionId){
		Assert.hasText(wechatUnionId, "unionId should not be null or empty");
		
		String tokenStr = this.tokenService.register(wechatUnionId);
		
		return new ApiObjectResult<>(tokenStr);
	}
}
