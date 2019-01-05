package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.List;

import com.sl.api.salon.model.db.SlShop;

public class ShopInfo implements Serializable {
	private static final long serialVersionUID = -1114508358837642398L;
	
	private Long shopId;
	private String shopNm;
	private String bdId;
	private Double shopLgtd;
	private Double shopLttd;
	private String shopLocation;
	private String shopPhone;
	/**
	 * start service time ( 10.5 means 10:30)
	 */
	private Double shopStm;
	/**
	 * end service time ( 22.5 means 22:30)
	 */
	private Double shopEtm;
	/**
	 * out of service time
	 */
	private List<ShopHoliday> holidays;
	
	private List<String> images;
	
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
	public String getShopPhone() {
		return shopPhone;
	}
	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
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
	public List<ShopHoliday> getHolidays() {
		return holidays;
	}
	public void setHolidays(List<ShopHoliday> holidays) {
		this.holidays = holidays;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	
	public ShopInfo(){
		super();
	}
	
	public ShopInfo(Long shopId, String shopNm, String bdId, Double shopLgtd,
			Double shopLttd, String shopLocation, String shopPhone, 
			Double shopStm, Double shopEtm) {
		super();
		this.shopId = shopId;
		this.shopNm = shopNm;
		this.bdId = bdId;
		this.shopLgtd = shopLgtd;
		this.shopLttd = shopLttd;
		this.shopLocation = shopLocation;
		this.shopPhone = shopPhone;
		this.shopStm = shopStm;
		this.shopEtm = shopEtm;
	}
	
	public ShopInfo(SlShop shop, List<ShopHoliday> holidays, List<String> images) {
		super();
		this.shopId = shop.getShopId();
		this.shopNm = shop.getShopNm();
		this.bdId = shop.getBdId();
		this.shopLgtd = shop.getShopLgtd();
		this.shopLttd = shop.getShopLttd();
		this.shopLocation = shop.getShopLocation();
		this.shopPhone = shop.getShopPhone();
		this.shopStm = shop.getShopStm();
		this.shopEtm = shop.getShopEtm();
		
		this.holidays = holidays;
		this.images = images;
	}
}
