package com.sl.api.salon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.model.SApiError;
import com.sl.api.salon.model.WePayOrderInfo;
import com.sl.api.salon.service.OrderService;
import com.sl.api.salon.service.WePayService;
import com.sl.common.filter.FilterHttpServletRequest;
import com.sl.common.model.db.SlOrder;
import com.zeasn.common.model.result.ApiError;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/payorder")
public class OrderPayController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private WePayService wePayService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ApiResult createOrder(@RequestParam Long odId, FilterHttpServletRequest request){
		Assert.notNull(odId, "odId should not be null or empty");
		
		SlOrder order = this.orderService.getOrderForPay(odId, request.getToken());
		if(order == null){
			return ApiResult.error(SApiError.ORDER_PAID_FAILED, "order cancelled or already paied.");
			
		}else{
			WePayOrderInfo payOrder = this.wePayService.payOrder(order, request.getToken());
			if(payOrder != null){
				return new ApiObjectResult<>(payOrder);
				
			}else{
				return ApiResult.error(ApiError.INTERNAL_ERROR, "create wepay order failed.");
			}
		}
	}
}
