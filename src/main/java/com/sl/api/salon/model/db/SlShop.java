package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlShop implements Serializable {
	private static final long serialVersionUID = 724393374242705090L;
	@Id
	private Long shopId;
	private String shopNm;
	private String bdId;
	private Integer shopEnable;
	private Double shopLgtd;
	private Double shopLttd;
	private String shopLocation;
	private String shopWechatpayId;
	private Long shopIcon;
	private Double shopStm;
	private Double shopEtm;
	private Long crtTs;
	private Long uptTs;
	
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopNm() {
		return shopNm;
	}
	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public Integer getShopEnable() {
		return shopEnable;
	}
	public void setShopEnable(Integer shopEnable) {
		this.shopEnable = shopEnable;
	}
	public Double getShopLgtd() {
		return shopLgtd;
	}
	public void setShopLgtd(Double shopLgtd) {
		this.shopLgtd = shopLgtd;
	}
	public Double getShopLttd() {
		return shopLttd;
	}
	public void setShopLttd(Double shopLttd) {
		this.shopLttd = shopLttd;
	}
	public String getShopLocation() {
		return shopLocation;
	}
	public void setShopLocation(String shopLocation) {
		this.shopLocation = shopLocation;
	}
	public String getShopWechatpayId() {
		return shopWechatpayId;
	}
	public void setShopWechatpayId(String shopWechatpayId) {
		this.shopWechatpayId = shopWechatpayId;
	}
	public Long getShopIcon() {
		return shopIcon;
	}
	public void setShopIcon(Long shopIcon) {
		this.shopIcon = shopIcon;
	}
	public Double getShopStm() {
		return shopStm;
	}
	public void setShopStm(Double shopStm) {
		this.shopStm = shopStm;
	}
	public Double getShopEtm() {
		return shopEtm;
	}
	public void setShopEtm(Double shopEtm) {
		this.shopEtm = shopEtm;
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
	
	public SlShop(){
		super();
	}
	
	public SlShop(Long shopId, String shopNm, String bdId, Integer shopEnable,
			Double shopLgtd, Double shopLttd, String shopLocation,
			String shopWechatpayId, Long shopIcon, 
			Double shopStm, Double shopEtm, Long crtTs, Long uptTs) {
		super();
		this.shopId = shopId;
		this.shopNm = shopNm;
		this.bdId = bdId;
		this.shopEnable = shopEnable;
		this.shopLgtd = shopLgtd;
		this.shopLttd = shopLttd;
		this.shopLocation = shopLocation;
		this.shopWechatpayId = shopWechatpayId;
		this.shopIcon = shopIcon;
		this.shopStm = shopStm;
		this.shopEtm = shopEtm;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
