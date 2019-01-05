package com.sl.api.salon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.filter.FilterHttpServletRequest;
import com.sl.api.salon.model.MemberInfo;
import com.sl.api.salon.service.MemberService;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(FilterHttpServletRequest request){
		MemberInfo memberInfo = this.memberService.getInfo(request.getToken());
		
		return new ApiObjectResult<>(memberInfo);
	}
}
