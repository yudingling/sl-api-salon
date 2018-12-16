package com.sl.api.salon.model;

import java.io.Serializable;

import com.sl.api.salon.model.db.SlShop;

public class ShopInfo implements Serializable {
	private static final long serialVersionUID = -1114508358837642398L;
	
	private Long shopId;
	private String shopNm;
	private String bdId;
	private Double shopLgtd;
	private Double shopLttd;
	private String shopLocation;
	private Long shopIcon;
	
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
	public Long getShopIcon() {
		return shopIcon;
	}
	public void setShopIcon(Long shopIcon) {
		this.shopIcon = shopIcon;
	}
	
	public ShopInfo(){
		super();
	}
	
	public ShopInfo(Long shopId, String shopNm, String bdId, Double shopLgtd,
			Double shopLttd, String shopLocation, Long shopIcon) {
		super();
		this.shopId = shopId;
		this.shopNm = shopNm;
		this.bdId = bdId;
		this.shopLgtd = shopLgtd;
		this.shopLttd = shopLttd;
		this.shopLocation = shopLocation;
		this.shopIcon = shopIcon;
	}
	
	public ShopInfo(SlShop shop) {
		super();
		this.shopId = shop.getShopId();
		this.shopNm = shop.getShopNm();
		this.bdId = shop.getBdId();
		this.shopLgtd = shop.getShopLgtd();
		this.shopLttd = shop.getShopLttd();
		this.shopLocation = shop.getShopLocation();
		this.shopIcon = shop.getShopIcon();
	}
}