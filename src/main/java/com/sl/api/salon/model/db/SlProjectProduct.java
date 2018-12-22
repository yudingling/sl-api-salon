package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlProjectProduct implements Serializable {
	private static final long serialVersionUID = -1946522709562249426L;
	@Id
	private Long pjpId;
	private Long pjId;
	private Long pdtpId;
	private Long pdId;
	private Long crtTs;
	private Long uptTs;
	
	public Long getPjpId() {
		return pjpId;
	}

	public void setPjpId(Long pjpId) {
		this.pjpId = pjpId;
	}

	public Long getPjId() {
		return pjId;
	}

	public void setPjId(Long pjId) {
		this.pjId = pjId;
	}

	public Long getPdtpId() {
		return pdtpId;
	}

	public void setPdtpId(Long pdtpId) {
		this.pdtpId = pdtpId;
	}

	public Long getPdId() {
		return pdId;
	}

	public void setPdId(Long pdId) {
		this.pdId = pdId;
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

	public SlProjectProduct(){
		super();
	}

	public SlProjectProduct(Long pjpId, Long pjId, Long pdtpId, Long pdId,
			Long crtTs, Long uptTs) {
		super();
		this.pjpId = pjpId;
		this.pjId = pjId;
		this.pdtpId = pdtpId;
		this.pdId = pdId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
}
