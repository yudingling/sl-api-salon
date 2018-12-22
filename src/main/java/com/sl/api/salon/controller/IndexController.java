package com.sl.api.salon.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.model.UserForToken;
import com.sl.api.salon.service.TokenService;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/index")
public class IndexController {
	@Autowired
	private TokenService tokenService;
	
	public ApiResult get(@RequestParam String brandId, @RequestParam String unionId, @RequestParam String nickName, 
			@RequestParam String avatarUrl, @RequestParam String phoneNumber, HttpServletRequest request, HttpServletResponse response) throws IOException{
		try{
			Assert.hasText(brandId, "brandId should not be null or empty");
			Assert.hasText(unionId, "unionId should not be null or empty");
			Assert.hasText(nickName, "nickName should not be null or empty");
			Assert.hasText(avatarUrl, "avatarUrl should not be null or empty");
			Assert.hasText(phoneNumber, "phoneNumber should not be null or empty");
			
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
			
			response.sendRedirect(this.tokenService.getRedirect(user, tokenStr));
			
		}catch(Exception ex){
			response.sendRedirect(this.tokenService.getRedirectForError());
		}
		
		return ApiResult.success();
	}
}
