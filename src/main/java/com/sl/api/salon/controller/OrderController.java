package com.sl.api.salon.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.filter.FilterHttpServletRequest;
import com.sl.api.salon.model.OrderInfo;
import com.sl.api.salon.model.SApiError;
import com.sl.api.salon.model.SToken;
import com.sl.api.salon.service.OrderService;
import com.sl.api.salon.service.ReservationService;
import com.zeasn.common.model.result.ApiError;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ApiResult post(@RequestParam Long rvId, @RequestParam(name = "pdIds[]", required = false) Set<Long> pdIds, FilterHttpServletRequest request){
		Assert.notNull(rvId, "rvId should not be null or empty");
		
		SToken token = request.getToken();
		
		if(this.reservationService.hasUnPaiedOrder(token)){
			return ApiResult.error(SApiError.ORDER_UNPAIED, "got unpaied order, please finish the unpaied order before create a new order!");
		}
		
		OrderInfo order = this.orderService.createOrder(token, rvId, pdIds);
		if(order == null){
			return ApiResult.error(ApiError.ARGUMENT_ERROR, "create order failed due to error arguments");
		}
		
		return new ApiObjectResult<>(order);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(FilterHttpServletRequest request){
		OrderInfo order = this.orderService.getCurrentOrder(request.getToken());
		
		return new ApiObjectResult<>(order);
	}
}
