package com.sl.api.salon.model;

import java.io.Serializable;

public class SigninResult implements Serializable {
	private static final long serialVersionUID = -2789000813310427409L;
	
	private String token;
	private String roleId;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public SigninResult(){
		super();
	}
	
	public SigninResult(String token, String roleId) {
		super();
		this.token = token;
		this.roleId = roleId;
	}
	
}
