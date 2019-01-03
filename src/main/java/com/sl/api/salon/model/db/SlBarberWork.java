package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlBarberWork implements Serializable {
	private static final long serialVersionUID = -17715001024108660L;
	@Id
	private Long bbwId;
	private Long uId;
	private String bdId;
	private Long bbwImg;
	private Long bbwLike;
	private Long crtTs;
	private Long uptTs;
	
	public Long getBbwId() {
		return bbwId;
	}
	public void setBbwId(Long bbwId) {
		this.bbwId = bbwId;
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
	public Long getBbwImg() {
		return bbwImg;
	}
	public void setBbwImg(Long bbwImg) {
		this.bbwImg = bbwImg;
	}
	public Long getBbwLike() {
		return bbwLike;
	}
	public void setBbwLike(Long bbwLike) {
		this.bbwLike = bbwLike;
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
	
	public SlBarberWork(){
		super();
	}
	
	public SlBarberWork(Long bbwId, Long uId, String bdId, Long bbwImg,
			Long bbwLike, Long crtTs, Long uptTs) {
		super();
		this.bbwId = bbwId;
		this.uId = uId;
		this.bdId = bdId;
		this.bbwImg = bbwImg;
		this.bbwLike = bbwLike;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
