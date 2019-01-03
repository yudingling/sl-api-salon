package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlBarberWorkLike implements Serializable {
	private static final long serialVersionUID = -3926177896345900815L;
	@Id
	private Long bbwlId;
	private Long bbwId;
	private Long uId;
	private Long crtTs;
	private Long uptTs;
	
	public Long getBbwlId() {
		return bbwlId;
	}
	public void setBbwlId(Long bbwlId) {
		this.bbwlId = bbwlId;
	}
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
	
	public SlBarberWorkLike(){
		super();
	}
	
	public SlBarberWorkLike(Long bbwlId, Long bbwId, Long uId, Long crtTs,
			Long uptTs) {
		super();
		this.bbwlId = bbwlId;
		this.bbwId = bbwId;
		this.uId = uId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
