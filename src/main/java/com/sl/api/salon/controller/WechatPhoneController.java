package com.sl.api.salon.controller;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sl.api.salon.model.SApiError;
import com.sl.api.salon.service.TokenService;
import com.sl.common.filter.FilterHttpServletRequest;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/wechat/phone")
public class WechatPhoneController {
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(@RequestParam String iv, @RequestParam String encryptedData, FilterHttpServletRequest request){
		Assert.hasText(iv, "iv should not be null or empty");
		Assert.hasText(encryptedData, "encryptedData should not be null or empty");
		
		String phone = this.tokenService.getWechatPhone(request.getToken(), iv, encryptedData);
		
		return StringUtils.isNotEmpty(phone) ? new ApiObjectResult<>(phone) : ApiResult.error(SApiError.GETPHONE_FAILED, "get user phone failed");
	}
}
