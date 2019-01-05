package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlShopImage implements Serializable {
	private static final long serialVersionUID = 8552959719397584479L;
	@Id
	private Long spiId;
	private String bdId;
	private Long shopId;
	private Long spiImg;
	private Long crtTs;
	private Long uptTs;
	
	public Long getSpiId() {
		return spiId;
	}
	public void setSpiId(Long spiId) {
		this.spiId = spiId;
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getSpiImg() {
		return spiImg;
	}
	public void setSpiImg(Long spiImg) {
		this.spiImg = spiImg;
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
	
	public SlShopImage(){
		super();
	}
	
	public SlShopImage(Long spiId, String bdId, Long shopId, Long spiImg,
			Long crtTs, Long uptTs) {
		super();
		this.spiId = spiId;
		this.bdId = bdId;
		this.shopId = shopId;
		this.spiImg = spiImg;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
