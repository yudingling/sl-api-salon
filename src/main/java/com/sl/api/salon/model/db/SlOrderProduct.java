package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlOrderProduct implements Serializable {
	private static final long serialVersionUID = 9007115468455802511L;
	@Id
	private Long odpId;
	private Long odId;
	private Long pdtpId;
	private Long pdId;
	private Double odpPrice;
	private Long crtTs;
	private Long uptTs;
	
	public Long getOdpId() {
		return odpId;
	}
	public void setOdpId(Long odpId) {
		this.odpId = odpId;
	}
	public Long getOdId() {
		return odId;
	}
	public void setOdId(Long odId) {
		this.odId = odId;
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
	public Double getOdpPrice() {
		return odpPrice;
	}
	public void setOdpPrice(Double odpPrice) {
		this.odpPrice = odpPrice;
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
	
	public SlOrderProduct(){
		super();
	}
	
	public SlOrderProduct(Long odpId, Long odId, Long pdtpId, Long pdId,
			Double odpPrice, Long crtTs, Long uptTs) {
		super();
		this.odpId = odpId;
		this.odId = odId;
		this.pdtpId = pdtpId;
		this.pdId = pdId;
		this.odpPrice = odpPrice;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
}
