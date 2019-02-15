package com.sl.api.salon.model;

import java.io.Serializable;

public class WePayOrderInfo implements Serializable {
	private static final long serialVersionUID = 1744509700893334135L;
	
	private String pkg;
	private String timeStamp;
	private String nonceStr;
	private String signType;
	private String paySign;
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	
	public WePayOrderInfo(){
		super();
	}
	
	public WePayOrderInfo(String pkg, String timeStamp, String nonceStr,
			String signType, String paySign) {
		super();
		this.pkg = pkg;
		this.timeStamp = timeStamp;
		this.nonceStr = nonceStr;
		this.signType = signType;
		this.paySign = paySign;
	}
	
}
