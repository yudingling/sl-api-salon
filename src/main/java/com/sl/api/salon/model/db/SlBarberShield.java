package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlBarberShield implements Serializable {
	private static final long serialVersionUID = 4995116216032655509L;
	@Id
	private Long bbtId;
	private Long uId;
	private Long bbtStm;
	private Long bbtEtm;
	private String bbtDesc;
	private Long crtTs;
	private Long uptTs;
	public Long getBbtId() {
		return bbtId;
	}
	public void setBbtId(Long bbtId) {
		this.bbtId = bbtId;
	}
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public Long getBbtStm() {
		return bbtStm;
	}
	public void setBbtStm(Long bbtStm) {
		this.bbtStm = bbtStm;
	}
	public Long getBbtEtm() {
		return bbtEtm;
	}
	public void setBbtEtm(Long bbtEtm) {
		this.bbtEtm = bbtEtm;
	}
	public String getBbtDesc() {
		return bbtDesc;
	}
	public void setBbtDesc(String bbtDesc) {
		this.bbtDesc = bbtDesc;
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
	
	public SlBarberShield(){
		super();
	}
	
	public SlBarberShield(Long bbtId, Long uId, Long bbtStm, Long bbtEtm,
			String bbtDesc, Long crtTs, Long uptTs) {
		super();
		this.bbtId = bbtId;
		this.uId = uId;
		this.bbtStm = bbtStm;
		this.bbtEtm = bbtEtm;
		this.bbtDesc = bbtDesc;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
