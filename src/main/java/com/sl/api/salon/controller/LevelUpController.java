package com.sl.api.salon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.model.WePayOrderInfo;
import com.sl.api.salon.service.LevelService;
import com.sl.api.salon.service.WePayService;
import com.sl.common.filter.FilterHttpServletRequest;
import com.sl.common.model.db.SlUserLevelOrder;
import com.zeasn.common.model.result.ApiError;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/levelup")
public class LevelUpController {
	@Autowired
	private LevelService levelService;
	@Autowired
	private WePayService wePayService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ApiResult createOrder(@RequestParam Long levelId, FilterHttpServletRequest request){
		Assert.notNull(levelId, "levelId should not be null or empty");
		
		SlUserLevelOrder order = this.levelService.createLevelUpOrder(request.getToken(), levelId);
		if(order == null){
			throw new IllegalArgumentException("level up failed.");
		}
		
		WePayOrderInfo payOrder = this.wePayService.payOrder(order, request.getToken());
		if(payOrder != null){
			return new ApiObjectResult<>(payOrder);
			
		}else{
			return ApiResult.error(ApiError.INTERNAL_ERROR, "create wepay order failed.");
		}
	}
}
