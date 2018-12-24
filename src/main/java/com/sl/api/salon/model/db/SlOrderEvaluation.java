package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlOrderEvaluation implements Serializable {
	private static final long serialVersionUID = -279458125775000224L;
	@Id
	private Long odevaId;
	private Long odId;
	private Integer odevaVal;
	private String odevaDesc;
	private Long crtTs;
	private Long uptTs;
	
	public Long getOdevaId() {
		return odevaId;
	}
	public void setOdevaId(Long odevaId) {
		this.odevaId = odevaId;
	}
	public Long getOdId() {
		return odId;
	}
	public void setOdId(Long odId) {
		this.odId = odId;
	}
	public Integer getOdevaVal() {
		return odevaVal;
	}
	public void setOdevaVal(Integer odevaVal) {
		this.odevaVal = odevaVal;
	}
	public String getOdevaDesc() {
		return odevaDesc;
	}
	public void setOdevaDesc(String odevaDesc) {
		this.odevaDesc = odevaDesc;
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
	
	public SlOrderEvaluation(){
		super();
	}
	
	public SlOrderEvaluation(Long odevaId, Long odId, Integer odevaVal,
			String odevaDesc, Long crtTs, Long uptTs) {
		super();
		this.odevaId = odevaId;
		this.odId = odId;
		this.odevaVal = odevaVal;
		this.odevaDesc = odevaDesc;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
}
