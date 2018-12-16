package com.sl.api.salon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.filter.FilterHttpServletRequest;
import com.sl.api.salon.model.db.SlLevel;
import com.sl.api.salon.service.LevelService;
import com.zeasn.common.model.result.ApiArrayResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/level")
public class LevelController {
	@Autowired
	private LevelService levelService;
	
	public ApiResult get(FilterHttpServletRequest request){
		List<SlLevel> levels = this.levelService.getLevels(request.getToken());
		
		return new ApiArrayResult<>(levels);
	}
}
