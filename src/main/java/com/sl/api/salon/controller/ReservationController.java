package com.sl.api.salon.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sl.api.salon.filter.FilterHttpServletRequest;
import com.sl.api.salon.model.ReservationInfo;
import com.sl.api.salon.model.SApiError;
import com.sl.api.salon.model.SToken;
import com.sl.api.salon.service.ReservationService;
import com.zeasn.common.model.result.ApiError;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ApiResult post(@RequestParam Long shopId, @RequestParam Long pjId, @RequestParam Long barberId, 
			@RequestParam(name = "pdIds[]", required = false) Set<Long> pdIds, @RequestParam Long stm, FilterHttpServletRequest request){
		Assert.notNull(shopId, "shopId should not be null or empty");
		Assert.notNull(pjId, "pjId should not be null or empty");
		Assert.notNull(barberId, "barberId should not be null or empty");
		Assert.notNull(stm, "stm should not be null or empty");
		
		SToken token = request.getToken();
		
		if(this.reservationService.hasUnPaiedOrder(token)){
			return ApiResult.error(SApiError.ORDER_UNPAIED, "got unpaied order, please finish the unpaied order before reserve!");
		}
		
		ReservationInfo reservation = this.reservationService.createReservation(token, shopId, pjId, barberId, stm, pdIds);
		if(reservation == null){
			return ApiResult.error(ApiError.ARGUMENT_ERROR, "create reservation failed due to error arguments");
		}
		
		return new ApiObjectResult<>(reservation);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(FilterHttpServletRequest request){
		ReservationInfo reservation = this.reservationService.getCurrentReservation(request.getToken());
		
		return new ApiObjectResult<>(reservation);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ApiResult delete(@RequestParam Long rvId, FilterHttpServletRequest request){
		Assert.notNull(rvId, "rvId should not be null or empty");
		
		boolean deleted = this.reservationService.deleteReservation(request.getToken(), rvId);
		
		return deleted ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR, "cancel reservation failed");
	}
}
