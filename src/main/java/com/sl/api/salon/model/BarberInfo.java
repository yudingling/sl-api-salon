package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.Map;

import com.sl.api.salon.model.db.SlUser;

public class BarberInfo implements Serializable {
	private static final long serialVersionUID = -1810139595415227453L;
	
	private Long uId;
	private String uNm;
	private String uPhone;
	private String iconUrl;
	private Map<Long, BarberProject> projects;
	/**
	 * key: stm, value: etm
	 */
	private Map<Long, Long> shieldTime;
	
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
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
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Map<Long, BarberProject> getProjects() {
		return projects;
	}
	public void setProjects(Map<Long, BarberProject> projects) {
		this.projects = projects;
	}
	public Map<Long, Long> getShieldTime() {
		return shieldTime;
	}
	public void setShieldTime(Map<Long, Long> shieldTime) {
		this.shieldTime = shieldTime;
	}
	
	public BarberInfo(){
		super();
	}
	
	public BarberInfo(Long uId, String uNm, String uPhone, String iconUrl,
			Map<Long, BarberProject> projects, Map<Long, Long> shieldTime) {
		super();
		this.uId = uId;
		this.uNm = uNm;
		this.uPhone = uPhone;
		this.iconUrl = iconUrl;
		this.projects = projects;
		this.shieldTime = shieldTime;
	}
	
	public BarberInfo(SlUser barber, String iconUrl, Map<Long, Long> shieldTime) {
		super();
		this.uId = barber.getuId();
		this.uNm = barber.getuNm();
		this.uPhone = barber.getuPhone();
		this.iconUrl = iconUrl;
		this.shieldTime = shieldTime;
	}
}
