package com.sl.api.salon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.service.EvaluationService;
import com.sl.common.filter.FilterHttpServletRequest;
import com.sl.common.model.SToken;
import com.zeasn.common.model.result.ApiError;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {
	@Autowired
	private EvaluationService evaluationService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ApiResult post(@RequestParam Long odId, @RequestParam Integer evaVal, @RequestParam(required = false) String evaDesc, FilterHttpServletRequest request){
		Assert.notNull(odId, "odId should not be null or empty");
		Assert.notNull(evaVal, "evaVal should not be null or empty");
		
		if(evaVal <= 0 || evaVal > 5){
			throw new IllegalArgumentException("evaVal must between 1~5");
		}
		
		SToken token = request.getToken();
		
		boolean ok = this.evaluationService.createEvaluation(token, odId, evaVal, evaDesc);
		
		return ok ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR, "evaluate order failed due to error arguments");
	}
}
