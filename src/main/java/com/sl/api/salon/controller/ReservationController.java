package com.sl.api.salon.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.sl.api.salon.model.ApiReservationFailedResult;
import com.sl.api.salon.model.ReservationInfo;
import com.sl.api.salon.model.SApiError;
import com.sl.api.salon.model.exception.BarberTimeShiledException;
import com.sl.api.salon.service.ReservationService;
import com.sl.common.filter.FilterHttpServletRequest;
import com.sl.common.model.SToken;
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
			@RequestParam(required = false) String pdIds, @RequestParam Long stm, FilterHttpServletRequest request){
		Assert.notNull(shopId, "shopId should not be null or empty");
		Assert.notNull(pjId, "pjId should not be null or empty");
		Assert.notNull(barberId, "barberId should not be null or empty");
		Assert.notNull(stm, "stm should not be null or empty");
		
		Set<Long> pdIdSet = StringUtils.isNotEmpty(pdIds) ? new HashSet<>(JSON.parseArray(pdIds, Long.class)) : null;
		
		SToken token = request.getToken();
		
		if(this.reservationService.isShopServiced(shopId)){
			return ApiResult.error(SApiError.SHOP_OUT_OF_SERVICE, "shop is out of service!");
		}
		
		if(this.reservationService.hasReservation(token)){
			return ApiResult.error(ApiError.ARGUMENT_ERROR, "there is already an reservation!");
		}
		
		if(this.reservationService.hasUnPaiedOrder(token)){
			return ApiResult.error(SApiError.ORDER_UNPAIED, "got unpaied order, please finish the unpaied order before reserve!");
		}
		
		try {
			ReservationInfo reservation = this.reservationService.createReservation(token, shopId, pjId, barberId, stm, pdIdSet);
			
			if(reservation == null){
				return ApiResult.error(ApiError.ARGUMENT_ERROR, "create reservation failed due to error arguments");
				
			}else{
				return new ApiObjectResult<>(reservation);
			}
			
		} catch (BarberTimeShiledException e) {
			return new ApiReservationFailedResult(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ApiResult get(FilterHttpServletRequest request){
		ReservationInfo reservation = this.reservationService.getCurrentReservation(request.getToken());
		
		return new ApiObjectResult<>(reservation);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(method = RequestMethod.DELETE)
	public ApiResult delete(@RequestBody String body, FilterHttpServletRequest request){
		Assert.hasText(body, "body should not be null or empty");
		
		Map data = JSON.parseObject(body, Map.class);
		Object rvIdStr = data.get("rvId");
		Assert.notNull(rvIdStr, "rvId should not be null or empty");
		
		Long rvId = Long.parseLong(rvIdStr.toString()) ;
		
		boolean deleted = this.reservationService.deleteReservation(request.getToken(), rvId);
		
		return deleted ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR, "cancel reservation failed");
	}
	
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ApiResult update(@RequestBody String body, FilterHttpServletRequest request){
		Assert.hasText(body, "body should not be null or empty");
		
		Map data = JSON.parseObject(body, Map.class);
		
		Object rvIdStr = data.get("rvId");
		Assert.notNull(rvIdStr, "rvId should not be null or empty");
		Long rvId = Long.parseLong(rvIdStr.toString()) ;
		
		Object oldPdIdStr = data.get("oldPdId");
		Assert.notNull(oldPdIdStr, "oldPdId should not be null or empty");
		Long oldPdId = Long.parseLong(oldPdIdStr.toString());
		
		Object newPdIdStr = data.get("newPdId");
		Assert.notNull(newPdIdStr, "newPdId should not be null or empty");
		Long newPdId = Long.parseLong(newPdIdStr.toString());
		
		boolean saved = this.reservationService.updateReservationProduct(request.getToken(), rvId, oldPdId, newPdId);
		
		return saved ? ApiResult.success() : ApiResult.error(ApiError.ARGUMENT_ERROR, "change product failed");
	}
}
