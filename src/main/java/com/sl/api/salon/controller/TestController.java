package com.sl.api.salon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sl.api.salon.model.SApiError;
import com.sl.api.salon.service.OrderService;
import com.sl.common.filter.FilterHttpServletRequest;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/confirmOrder", method = RequestMethod.GET)
	public ApiResult update(@RequestParam Long odId, FilterHttpServletRequest request){
		Assert.notNull(odId, "odId should not be null or empty");
		
		Boolean ok = this.orderService.activeOrder(request.getToken(), odId);
		
		return ok ? ApiResult.success() : ApiResult.error(SApiError.ORDER_CONFIRM_FAILED, "confirm order failed");
	}
}
