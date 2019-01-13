package com.sl.api.salon.model;

import java.io.Serializable;

public class WeChatSession implements Serializable {
	private static final long serialVersionUID = -8630167958635565444L;
	
	private String openId;
	private String sessionKey;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	public WeChatSession(){
		super();
	}
	
	public WeChatSession(String openId, String sessionKey) {
		super();
		this.openId = openId;
		this.sessionKey = sessionKey;
	}
	
}
