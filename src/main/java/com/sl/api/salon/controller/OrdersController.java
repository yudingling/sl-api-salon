package com.sl.api.salon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.model.HistoryOrder;
import com.sl.api.salon.model.OrderInfo;
import com.sl.api.salon.service.OrderService;
import com.sl.common.filter.FilterHttpServletRequest;
import com.zeasn.common.model.result.ApiArrayResult;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(@RequestParam Integer startIndex, @RequestParam Integer size, FilterHttpServletRequest request){
		Assert.notNull(startIndex, "startIndex should not be null or empty");
		Assert.notNull(size, "size should not be null or empty");
		
		List<HistoryOrder> orders = this.orderService.getHistoryOrders(request.getToken(), startIndex, size);
		
		return new ApiArrayResult<>(orders);
	}
	
	@RequestMapping("/{odId}")
	public ApiResult get(@PathVariable Long odId, FilterHttpServletRequest request){
		Assert.notNull(odId, "odId should not be null or empty");
		
		OrderInfo info = this.orderService.getHistoryOrder(request.getToken(), odId);
		
		return new ApiObjectResult<>(info);
	}
}
