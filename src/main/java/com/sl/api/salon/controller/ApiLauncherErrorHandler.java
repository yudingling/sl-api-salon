package com.sl.api.salon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import com.zeasn.common.apilog.ApiLog;
import com.zeasn.common.model.result.ApiError;
import com.zeasn.common.model.result.ApiResult;

@RestController
@ControllerAdvice
public class ApiLauncherErrorHandler {
	
	@ExceptionHandler({IllegalArgumentException.class, ServletRequestBindingException.class})
	public ApiResult illegalArgumentException(HttpServletRequest request, HttpServletResponse response, Exception e){
		ApiLog.logRequest(request, response, e);
		
		return ApiResult.error(ApiError.ARGUMENT_ERROR, e.getMessage());
	}
	
	@ExceptionHandler(value = Exception.class)
	public ApiResult exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e){
		ApiLog.logRequest(request, response, e);
		
		return ApiResult.error(ApiError.INTERNAL_ERROR);
	}
}
