package com.sl.api.salon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.sl.api.salon.model.SigninResult;
import com.sl.api.salon.service.TokenService;
import com.sl.common.model.UserForToken;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/signin")
public class SigninController {
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping("/wechat")
	public ApiResult get(@RequestParam String brandId, @RequestParam String code, @RequestParam String nickName, 
			@RequestParam String avatarUrl, @RequestParam(required = false) String phoneNumber, 
			HttpServletRequest request, HttpServletResponse response){
		
		Assert.hasText(brandId, "brandId should not be null or empty");
		Assert.hasText(code, "code should not be null or empty");
		Assert.hasText(nickName, "nickName should not be null or empty");
		Assert.hasText(avatarUrl, "avatarUrl should not be null or empty");
		
		String unionId = this.tokenService.getWechatUnionId(brandId, code);
		if(StringUtils.isEmpty(unionId)){
			throw new IllegalArgumentException("wechat verify failed.");
		}
		
		UserForToken user = this.tokenService.getUser(unionId);
		if(user == null){
			user = this.tokenService.registerUser(brandId, unionId, nickName, avatarUrl, phoneNumber);
			if(user == null){
				throw new IllegalArgumentException("user register failed.");
			}
			
		}else if(!avatarUrl.equals(user.getuAvatar())){
			this.tokenService.updateUserAvatar(user.getuId(), avatarUrl);
		}
		
		String tokenStr = this.tokenService.register(user);
		
		return new ApiObjectResult<>(new SigninResult(tokenStr, user.getRoleId()));
	}
	
	@RequestMapping("/normal")
	public ApiResult get(@RequestParam String phoneNumber, @RequestParam String password, HttpServletRequest request, HttpServletResponse response){
		Assert.hasText(phoneNumber, "phoneNumber should not be null or empty");
		Assert.hasText(password, "password should not be null or empty");
		
		
		return new ApiObjectResult<>(null);
	}
}
