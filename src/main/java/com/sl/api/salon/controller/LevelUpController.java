package com.sl.api.salon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.service.LevelService;
import com.sl.common.filter.FilterHttpServletRequest;
import com.sl.common.model.db.SlUserLevelOrder;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/levelup")
public class LevelUpController {
	@Autowired
	private LevelService levelService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ApiResult createOrder(@RequestParam Long levelId, FilterHttpServletRequest request){
		Assert.notNull(levelId, "levelId should not be null or empty");
		
		SlUserLevelOrder order = this.levelService.createLevelUpOrder(request.getToken(), levelId);
		if(order == null){
			throw new IllegalArgumentException("level up failed.");
		}
		
		//&&&&&&&&&&& todo.  此处还需要返回特约商户的相关信息，需要支付到特约商户 (可能。 需要确定特约商户支付的相关流程)
		
		return new ApiObjectResult<>(order);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ApiResult paied(@RequestParam Long ulodId, FilterHttpServletRequest request){
		Assert.notNull(ulodId, "ulodId should not be null or empty");
		
		if(this.levelService.levelUpPaied(request.getToken(), ulodId)){
			return ApiResult.success();
			
		}else{
			throw new IllegalArgumentException("level up failed.");
		}
	}
}
