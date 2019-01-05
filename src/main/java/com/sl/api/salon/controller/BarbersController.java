package com.sl.api.salon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.filter.FilterHttpServletRequest;
import com.sl.api.salon.model.BarberReservation;
import com.sl.api.salon.service.BarberService;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/barbers")
public class BarbersController {
	@Autowired
	private BarberService barberService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(@RequestParam Long shopId, FilterHttpServletRequest request){
		Assert.notNull(shopId, "shopId should not be null or empty");
		
		BarberReservation br = this.barberService.getBarbers(request.getToken(), shopId);
		
		return new ApiObjectResult<>(br);
	}
}
