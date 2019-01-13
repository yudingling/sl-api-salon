package com.sl.api.salon.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.sl.api.salon.model.WeChatSession;
import com.sl.api.salon.service.TokenService;
import com.sl.common.model.UserForToken;

@Controller
@RequestMapping("/index")
public class IndexController {
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(method = RequestMethod.GET)
	public void get(@RequestParam String brandId, @RequestParam String code, @RequestParam String nickName, 
			@RequestParam String avatarUrl, @RequestParam(required = false) String phoneNumber, 
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		String domain = this.getDomain(request);
		
		try{
			Assert.hasText(brandId, "brandId should not be null or empty");
			Assert.hasText(code, "code should not be null or empty");
			Assert.hasText(nickName, "nickName should not be null or empty");
			Assert.hasText(avatarUrl, "avatarUrl should not be null or empty");
			
			WeChatSession session = this.tokenService.getWechatSession(brandId, code);
			if(session == null){
				throw new IllegalArgumentException("wechat verify failed.");
			}
			
			UserForToken user = this.tokenService.getUser(session.getOpenId());
			if(user == null){
				user = this.tokenService.registerUser(brandId, session.getOpenId(), nickName, avatarUrl, phoneNumber);
				if(user == null){
					throw new IllegalArgumentException("user register failed.");
				}
				
			}else if(!avatarUrl.equals(user.getuAvatar())){
				this.tokenService.updateUserAvatar(user.getuId(), avatarUrl);
			}
			
			String tokenStr = this.tokenService.createToken(user, session);
			
			response.sendRedirect(this.tokenService.getRedirect(domain, user, tokenStr));
			
		}catch(Exception ex){
			response.sendError(HttpStatus.SC_BAD_REQUEST, ex.getMessage());
		}
	}
	
	private String getDomain(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
	}
}
