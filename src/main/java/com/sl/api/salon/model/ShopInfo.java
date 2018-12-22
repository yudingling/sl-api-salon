package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.List;

import com.sl.api.salon.model.db.SlShop;
import com.sl.api.salon.model.db.SlShopHoliday;

public class ShopInfo implements Serializable {
	private static final long serialVersionUID = -1114508358837642398L;
	
	private Long shopId;
	private String shopNm;
	private String bdId;
	private Double shopLgtd;
	private Double shopLttd;
	private String shopLocation;
	private Long shopIcon;
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
	private List<SlShopHoliday> holidays;
	
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
	public List<SlShopHoliday> getHolidays() {
		return holidays;
	}
	public void setHolidays(List<SlShopHoliday> holidays) {
		this.holidays = holidays;
	}
	
	public ShopInfo(){
		super();
	}
	
	public ShopInfo(Long shopId, String shopNm, String bdId, Double shopLgtd,
			Double shopLttd, String shopLocation, Long shopIcon,
			Double shopStm, Double shopEtm) {
		super();
		this.shopId = shopId;
		this.shopNm = shopNm;
		this.bdId = bdId;
		this.shopLgtd = shopLgtd;
		this.shopLttd = shopLttd;
		this.shopLocation = shopLocation;
		this.shopIcon = shopIcon;
		this.shopStm = shopStm;
		this.shopEtm = shopEtm;
	}
	
	public ShopInfo(SlShop shop, List<SlShopHoliday> holidays) {
		super();
		this.shopId = shop.getShopId();
		this.shopNm = shop.getShopNm();
		this.bdId = shop.getBdId();
		this.shopLgtd = shop.getShopLgtd();
		this.shopLttd = shop.getShopLttd();
		this.shopLocation = shop.getShopLocation();
		this.shopIcon = shop.getShopIcon();
		this.shopStm = shop.getShopStm();
		this.shopEtm = shop.getShopEtm();
		
		this.holidays = holidays;
	}
}
