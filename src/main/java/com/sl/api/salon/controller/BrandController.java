package com.sl.api.salon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.model.BrandInfo;
import com.sl.api.salon.service.BrandService;
import com.sl.common.filter.FilterHttpServletRequest;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
	@Autowired
	private BrandService brandService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(@RequestParam Double lgtd, @RequestParam Double lttd, FilterHttpServletRequest request){
		Assert.notNull(lgtd, "lgtd should not be null or empty");
		Assert.notNull(lttd, "lttd should not be null or empty");
		
		BrandInfo brandInfo = this.brandService.getBrandInfo(request.getToken(), lgtd, lttd);
		
		return new ApiObjectResult<>(brandInfo);
	}
}
