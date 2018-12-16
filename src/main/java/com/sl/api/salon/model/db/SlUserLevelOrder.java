package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlUserLevelOrder implements Serializable {
	private static final long serialVersionUID = 6368935127520899020L;
	@Id
	private Long ulodId;
	private Long uId;
	private Long levelId;
	private Long ulodStm;
	private Long ulodEtm;
	private String ulodDuration;
	private Double ulodDiscount;
	private Double ulodPrice;
	private Long ulodPaiedTs;
	private String ulodPaiedTp;
	private Long crtTs;
	private Long uptTs;
	
	public Long getUlodId() {
		return ulodId;
	}

	public void setUlodId(Long ulodId) {
		this.ulodId = ulodId;
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

	public Long getUlodStm() {
		return ulodStm;
	}

	public void setUlodStm(Long ulodStm) {
		this.ulodStm = ulodStm;
	}

	public Long getUlodEtm() {
		return ulodEtm;
	}

	public void setUlodEtm(Long ulodEtm) {
		this.ulodEtm = ulodEtm;
	}

	public String getUlodDuration() {
		return ulodDuration;
	}

	public void setUlodDuration(String ulodDuration) {
		this.ulodDuration = ulodDuration;
	}

	public Double getUlodDiscount() {
		return ulodDiscount;
	}

	public void setUlodDiscount(Double ulodDiscount) {
		this.ulodDiscount = ulodDiscount;
	}

	public Double getUlodPrice() {
		return ulodPrice;
	}

	public void setUlodPrice(Double ulodPrice) {
		this.ulodPrice = ulodPrice;
	}

	public Long getUlodPaiedTs() {
		return ulodPaiedTs;
	}

	public void setUlodPaiedTs(Long ulodPaiedTs) {
		this.ulodPaiedTs = ulodPaiedTs;
	}

	public String getUlodPaiedTp() {
		return ulodPaiedTp;
	}

	public void setUlodPaiedTp(String ulodPaiedTp) {
		this.ulodPaiedTp = ulodPaiedTp;
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

	public SlUserLevelOrder(){
		super();
	}

	public SlUserLevelOrder(Long ulodId, Long uId, Long levelId, Long ulodStm,
			Long ulodEtm, String ulodDuration, Double ulodDiscount,
			Double ulodPrice, Long ulodPaiedTs, String ulodPaiedTp, Long crtTs,
			Long uptTs) {
		super();
		this.ulodId = ulodId;
		this.uId = uId;
		this.levelId = levelId;
		this.ulodStm = ulodStm;
		this.ulodEtm = ulodEtm;
		this.ulodDuration = ulodDuration;
		this.ulodDiscount = ulodDiscount;
		this.ulodPrice = ulodPrice;
		this.ulodPaiedTs = ulodPaiedTs;
		this.ulodPaiedTp = ulodPaiedTp;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
}
