package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlShopHoliday implements Serializable {
	private static final long serialVersionUID = -526298778037502548L;
	@Id
	private Long sofId;
	private Long shopId;
	private Long sofStm;
	private Long sofEtm;
	private Long crtTs;
	private Long uptTs;
	
	public Long getSofId() {
		return sofId;
	}
	public void setSofId(Long sofId) {
		this.sofId = sofId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getSofStm() {
		return sofStm;
	}
	public void setSofStm(Long sofStm) {
		this.sofStm = sofStm;
	}
	public Long getSofEtm() {
		return sofEtm;
	}
	public void setSofEtm(Long sofEtm) {
		this.sofEtm = sofEtm;
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
	
	public SlShopHoliday(){
		super();
	}
	
	public SlShopHoliday(Long sofId, Long shopId, Long sofStm, Long sofEtm,
			Long crtTs, Long uptTs) {
		super();
		this.sofId = sofId;
		this.shopId = shopId;
		this.sofStm = sofStm;
		this.sofEtm = sofEtm;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
