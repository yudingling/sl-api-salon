package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlUserBarber implements Serializable {
	private static final long serialVersionUID = -6164328853429186863L;
	@Id
	private Long ubId;
	private Long shopId;
	private Long uId;
	private String bdId;
	private Long crtTs;
	private Long uptTs;
	
	public Long getUbId() {
		return ubId;
	}
	public void setUbId(Long ubId) {
		this.ubId = ubId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
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
	
	public SlUserBarber(){
		super();
	}
	
	public SlUserBarber(Long ubId, Long shopId, Long uId, String bdId,
			Long crtTs, Long uptTs) {
		super();
		this.ubId = ubId;
		this.shopId = shopId;
		this.uId = uId;
		this.bdId = bdId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
