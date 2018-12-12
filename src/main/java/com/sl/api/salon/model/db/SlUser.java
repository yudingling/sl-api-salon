package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlUser implements Serializable {
	private static final long serialVersionUID = -2494166350121394270L;
	@Id
	private Long uId;
	private String bdId;
	private String uNm;
	private String uPhone;
	private String uEmail;
	private String uPwd;
	private String roleId;
	private Integer uActive;
	private Integer uDisabled;
	private Long uIcon;
	private Long crtTs;
	private Long uptTs;
	
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

	public String getuNm() {
		return uNm;
	}

	public void setuNm(String uNm) {
		this.uNm = uNm;
	}

	public String getuPhone() {
		return uPhone;
	}

	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}

	public String getuEmail() {
		return uEmail;
	}

	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	public String getuPwd() {
		return uPwd;
	}

	public void setuPwd(String uPwd) {
		this.uPwd = uPwd;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Integer getuActive() {
		return uActive;
	}

	public void setuActive(Integer uActive) {
		this.uActive = uActive;
	}

	public Integer getuDisabled() {
		return uDisabled;
	}

	public void setuDisabled(Integer uDisabled) {
		this.uDisabled = uDisabled;
	}

	public Long getuIcon() {
		return uIcon;
	}

	public void setuIcon(Long uIcon) {
		this.uIcon = uIcon;
	}

	public Long getCrtTs() {
		return crtTs;
	}

	public void setCrtTs(Long crtTs) {
		this.crtTs = crtTs;
	}

	public Long getUptTs() {
		return uptTs;
	}

	public void setUptTs(Long uptTs) {
		this.uptTs = uptTs;
	}

	public SlUser(){
		super();
	}

	public SlUser(Long uId, String bdId, String uNm, String uPhone,
			String uEmail, String uPwd, String roleId, Integer uActive,
			Integer uDisabled, Long uIcon, Long crtTs, Long uptTs) {
		super();
		this.uId = uId;
		this.bdId = bdId;
		this.uNm = uNm;
		this.uPhone = uPhone;
		this.uEmail = uEmail;
		this.uPwd = uPwd;
		this.roleId = roleId;
		this.uActive = uActive;
		this.uDisabled = uDisabled;
		this.uIcon = uIcon;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
