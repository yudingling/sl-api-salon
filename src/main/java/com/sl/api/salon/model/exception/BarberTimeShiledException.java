package com.sl.api.salon.model.exception;

import java.util.Map;

public class BarberTimeShiledException extends Exception {
	private static final long serialVersionUID = 8612890386302257507L;
	
	private Long barberId;
	/**
	 * key: stm, value: etm
	 */
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

	public BarberTimeShiledException(Long barberId, Map<Long, Long> shieldTime) {
		super();
		
		this.barberId = barberId;
		this.shieldTime = shieldTime;
	}
}
