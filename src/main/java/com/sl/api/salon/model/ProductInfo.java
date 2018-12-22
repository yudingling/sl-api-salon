package com.sl.api.salon.model;

import java.io.Serializable;

import com.sl.api.salon.model.db.SlProduct;

public class ProductInfo implements Serializable {
	private static final long serialVersionUID = -2470885734004580495L;
	
	private Long pdId;
	private String pdNm;
	private Long pdtpId;
	private String pdDesc;
	private String pdIconUrl;
	private Double pdPrice;
	
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
	public String getPdIconUrl() {
		return pdIconUrl;
	}
	public void setPdIconUrl(String pdIconUrl) {
		this.pdIconUrl = pdIconUrl;
	}
	public Double getPdPrice() {
		return pdPrice;
	}
	public void setPdPrice(Double pdPrice) {
		this.pdPrice = pdPrice;
	}
	
	public ProductInfo(){
		super();
	}
	
	public ProductInfo(Long pdId, String pdNm, Long pdtpId, String pdDesc,
			String pdIconUrl, Double pdPrice) {
		super();
		this.pdId = pdId;
		this.pdNm = pdNm;
		this.pdtpId = pdtpId;
		this.pdDesc = pdDesc;
		this.pdIconUrl = pdIconUrl;
		this.pdPrice = pdPrice;
	}
	
	public ProductInfo(SlProduct product, String pdIconUrl) {
		super();
		this.pdId = product.getPdId();
		this.pdNm = product.getPdNm();
		this.pdtpId = product.getPdtpId();
		this.pdDesc = product.getPdDesc();
		this.pdIconUrl = pdIconUrl;
		this.pdPrice = product.getPdPrice();
	}
}
