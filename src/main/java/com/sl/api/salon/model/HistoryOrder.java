package com.sl.api.salon.model;

import java.io.Serializable;

public class HistoryOrder implements Serializable {
	private static final long serialVersionUID = 4774303199208014058L;
	
	private Long odId;
	private Long shopId;
	private String shopNm;
	private Long odUid;
	private Long odStm;
	private Long pjId;
	private String pjNm;
	private Double odTotalPrice;
	private Integer odEva;
	
	public Long getOdId() {
		return odId;
	}

	public void setOdId(Long odId) {
		this.odId = odId;
	}

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

	public Long getOdUid() {
		return odUid;
	}

	public void setOdUid(Long odUid) {
		this.odUid = odUid;
	}

	public Long getOdStm() {
		return odStm;
	}

	public void setOdStm(Long odStm) {
		this.odStm = odStm;
	}

	public Long getPjId() {
		return pjId;
	}

	public void setPjId(Long pjId) {
		this.pjId = pjId;
	}

	public String getPjNm() {
		return pjNm;
	}

	public void setPjNm(String pjNm) {
		this.pjNm = pjNm;
	}

	public Double getOdTotalPrice() {
		return odTotalPrice;
	}

	public void setOdTotalPrice(Double odTotalPrice) {
		this.odTotalPrice = odTotalPrice;
	}

	public Integer getOdEva() {
		return odEva;
	}

	public void setOdEva(Integer odEva) {
		this.odEva = odEva;
	}

	public HistoryOrder(){
		super();
	}
	
	public HistoryOrder(Long odId, Long shopId, String shopNm, Long odUid,
			Long odStm, Long pjId, String pjNm, Double odTotalPrice,
			Integer odEva) {
		super();
		this.odId = odId;
		this.shopId = shopId;
		this.shopNm = shopNm;
		this.odUid = odUid;
		this.odStm = odStm;
		this.pjId = pjId;
		this.pjNm = pjNm;
		this.odTotalPrice = odTotalPrice;
		this.odEva = odEva;
	}
}
