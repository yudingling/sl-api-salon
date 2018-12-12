package com.sl.api.salon.model.token;

import java.io.Serializable;

public class UserForToken implements Serializable {
	private static final long serialVersionUID = -8582698753105033130L;
	
	private Long uId;
	private String bdId;
	private String roleId;
	
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public UserForToken(){
		super();
	}
	
	public UserForToken(Long uId, String bdId, String roleId) {
		super();
		this.uId = uId;
		this.bdId = bdId;
		this.roleId = roleId;
	}
	
}
