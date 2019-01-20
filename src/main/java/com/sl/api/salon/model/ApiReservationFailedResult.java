package com.sl.api.salon.model;

import java.util.Map;

import com.sl.api.salon.model.exception.BarberTimeShiledException;
import com.zeasn.common.model.result.ApiResult;

public class ApiReservationFailedResult extends ApiResult {
	private static final long serialVersionUID = -2478134498618883174L;
	
	private Long barberId;
	private Map<Long, Long> shieldTime;

	public Long getBarberId() {
		return barberId;
	}

	public void setBarberId(Long barberId) {
		this.barberId = barberId;
	}

	public Map<Long, Long> getShieldTime() {
		return shieldTime;
	}

	public void setShieldTime(Map<Long, Long> shieldTime) {
		this.shieldTime = shieldTime;
	}
	
	public ApiReservationFailedResult() {
		super();
	}

	public ApiReservationFailedResult(BarberTimeShiledException e) {
		super(SApiError.RESERVATION_TIME_SHILED, "reservation failed for barber time shiled");
		
		this.barberId = e.getBarberId();
		this.shieldTime = e.getShieldTime();
	}
}
