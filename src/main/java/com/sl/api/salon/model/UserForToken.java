package com.sl.api.salon.model;

import java.io.Serializable;

public class UserForToken implements Serializable {
	private static final long serialVersionUID = -8582698753105033130L;
	
	private Long uId;
	private String bdId;
	private String roleId;
	private String uAvatar;
	
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
	public String getuAvatar() {
		return uAvatar;
	}
	public void setuAvatar(String uAvatar) {
		this.uAvatar = uAvatar;
	}
	
	public UserForToken(){
		super();
	}
	
	public UserForToken(Long uId, String bdId, String roleId, String uAvatar) {
		super();
		this.uId = uId;
		this.bdId = bdId;
		this.roleId = roleId;
		this.uAvatar = uAvatar;
	}
	
}
