package com.sl.api.salon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.filter.FilterHttpServletRequest;
import com.sl.api.salon.model.BarberWork;
import com.sl.api.salon.service.BarberWorkService;
import com.zeasn.common.model.result.ApiArrayResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/barber/work")
public class BarberWorkController {
	@Autowired
	private BarberWorkService workService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(@RequestParam Long shopId, @RequestParam Integer startIndex, @RequestParam Integer size, 
			@RequestParam Integer sortType, FilterHttpServletRequest request){
		Assert.notNull(shopId, "shopId should not be null or empty");
		Assert.notNull(startIndex, "startIndex should not be null or empty");
		Assert.notNull(size, "size should not be null or empty");
		Assert.notNull(sortType, "sortType should not be null or empty");
		
		List<BarberWork> workds = this.workService.getWorks(request.getToken(), shopId, startIndex, size, sortType);
		
		return new ApiArrayResult<>(workds);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ApiResult update(@RequestParam Long workId, @RequestParam Boolean like, FilterHttpServletRequest request){
		Assert.notNull(workId, "workId should not be null or empty");
		Assert.notNull(like, "like should not be null or empty");
		
		if(like){
			this.workService.likeWork(request.getToken(), workId);
			
		}else{
			this.workService.dislikeWork(request.getToken(), workId);
		}
		
		return ApiResult.success();
	}
}
