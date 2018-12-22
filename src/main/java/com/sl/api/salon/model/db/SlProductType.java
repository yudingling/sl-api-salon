package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlProductType implements Serializable {
	private static final long serialVersionUID = -5257512455906390268L;
	@Id
	private Long pdtpId;
	private String pdtpNm;
	private String bdId;
	private Long crtTs;
	private Long uptTs;
	
	public Long getPdtpId() {
		return pdtpId;
	}
	public void setPdtpId(Long pdtpId) {
		this.pdtpId = pdtpId;
	}
	public String getPdtpNm() {
		return pdtpNm;
	}
	public void setPdtpNm(String pdtpNm) {
		this.pdtpNm = pdtpNm;
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
	
	public SlProductType(){
		super();
	}
	
	public SlProductType(Long pdtpId, String pdtpNm, String bdId, Long crtTs,
			Long uptTs) {
		super();
		this.pdtpId = pdtpId;
		this.pdtpNm = pdtpNm;
		this.bdId = bdId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
