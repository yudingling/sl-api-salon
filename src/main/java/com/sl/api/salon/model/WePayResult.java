package com.sl.api.salon.model;

import java.io.Serializable;

public class WePayResult implements Serializable {
	private static final long serialVersionUID = 2034314768328708560L;
	
	private String tradeNo;
	private Boolean success;
	
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public WePayResult(){
		super();
	}
	
	public WePayResult(String tradeNo, Boolean success) {
		super();
		this.tradeNo = tradeNo;
		this.success = success;
	}
	
}
