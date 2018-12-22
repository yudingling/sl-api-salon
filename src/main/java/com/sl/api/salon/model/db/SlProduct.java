package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlProduct implements Serializable {
	private static final long serialVersionUID = 5535379476189794062L;
	@Id
	private Long pdId;
	private String pdNm;
	private String bdId;
	private Long pdtpId;
	private String pdDesc;
	private Long pdIcon;
	private Double pdPrice;
	private Integer pdEnable;
	private Long crtTs;
	private Long uptTs;
	
	public Long getPdId() {
		return pdId;
	}
	public void setPdId(Long pdId) {
		this.pdId = pdId;
	}
	public String getPdNm() {
		return pdNm;
	}
	public void setPdNm(String pdNm) {
		this.pdNm = pdNm;
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public Long getPdtpId() {
		return pdtpId;
	}
	public void setPdtpId(Long pdtpId) {
		this.pdtpId = pdtpId;
	}
	public String getPdDesc() {
		return pdDesc;
	}
	public void setPdDesc(String pdDesc) {
		this.pdDesc = pdDesc;
	}
	public Long getPdIcon() {
		return pdIcon;
	}
	public void setPdIcon(Long pdIcon) {
		this.pdIcon = pdIcon;
	}
	public Double getPdPrice() {
		return pdPrice;
	}
	public void setPdPrice(Double pdPrice) {
		this.pdPrice = pdPrice;
	}
	public Integer getPdEnable() {
		return pdEnable;
	}
	public void setPdEnable(Integer pdEnable) {
		this.pdEnable = pdEnable;
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
	
	public SlProduct(){
		super();
	}
	
	public SlProduct(Long pdId, String pdNm, String bdId, Long pdtpId,
			String pdDesc, Long pdIcon, Double pdPrice, Integer pdEnable,
			Long crtTs, Long uptTs) {
		super();
		this.pdId = pdId;
		this.pdNm = pdNm;
		this.bdId = bdId;
		this.pdtpId = pdtpId;
		this.pdDesc = pdDesc;
		this.pdIcon = pdIcon;
		this.pdPrice = pdPrice;
		this.pdEnable = pdEnable;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
