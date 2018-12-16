package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlUserLevel implements Serializable {
	private static final long serialVersionUID = 7527689103594582239L;
	@Id
	private Long ulId;
	private Long uId;
	private Long levelId;
	private Long ulStm;
	private Long ulEtm;
	private Double ulDiscount;
	private Integer ulAvailable;
	private Long crtTs;
	private Long uptTs;
	
	public Long getUlId() {
		return ulId;
	}

	public void setUlId(Long ulId) {
		this.ulId = ulId;
	}

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public Long getUlStm() {
		return ulStm;
	}

	public void setUlStm(Long ulStm) {
		this.ulStm = ulStm;
	}

	public Long getUlEtm() {
		return ulEtm;
	}

	public void setUlEtm(Long ulEtm) {
		this.ulEtm = ulEtm;
	}

	public Double getUlDiscount() {
		return ulDiscount;
	}

	public void setUlDiscount(Double ulDiscount) {
		this.ulDiscount = ulDiscount;
	}

	public Integer getUlAvailable() {
		return ulAvailable;
	}

	public void setUlAvailable(Integer ulAvailable) {
		this.ulAvailable = ulAvailable;
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

	public SlUserLevel(){
		super();
	}

	public SlUserLevel(Long ulId, Long uId, Long levelId, Long ulStm,
			Long ulEtm, Double ulDiscount, Integer ulAvailable, Long crtTs,
			Long uptTs) {
		super();
		this.ulId = ulId;
		this.uId = uId;
		this.levelId = levelId;
		this.ulStm = ulStm;
		this.ulEtm = ulEtm;
		this.ulDiscount = ulDiscount;
		this.ulAvailable = ulAvailable;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
}
