package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlReservationProduct implements Serializable {
	private static final long serialVersionUID = 6580257580097615921L;
	@Id
	private Long rvpId;
	private Long rvId;
	private Long pdtpId;
	private Long pdId;
	private Long crtTs;
	private Long uptTs;
	public Long getRvpId() {
		return rvpId;
	}
	public void setRvpId(Long rvpId) {
		this.rvpId = rvpId;
	}
	public Long getRvId() {
		return rvId;
	}
	public void setRvId(Long rvId) {
		this.rvId = rvId;
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
	
	public SlReservationProduct(){
		super();
	}
	
	public SlReservationProduct(Long rvpId, Long rvId, Long pdtpId, Long pdId,
			Long crtTs, Long uptTs) {
		super();
		this.rvpId = rvpId;
		this.rvId = rvId;
		this.pdtpId = pdtpId;
		this.pdId = pdId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
