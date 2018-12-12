package com.sl.api.salon.model;

import java.io.Serializable;

import com.sl.api.salon.model.token.UserForToken;

public class SToken implements Serializable {
	private static final long serialVersionUID = -3597832464940512081L;
	
	private String brandId;
	private Long userId;
	private UserType userType;
	
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	public SToken(){
		super();
	}
	
	public SToken(String brandId, Long userId, UserType userType) {
		super();
		this.brandId = brandId;
		this.userId = userId;
		this.userType = userType;
	}
	
	public SToken(UserForToken token) {
		super();
		this.brandId = token.getBdId();
		this.userId = token.getuId();
		this.userType = UserType.valueOf(token.getRoleId());
	}
}
