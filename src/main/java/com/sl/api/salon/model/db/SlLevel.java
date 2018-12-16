package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlLevel implements Serializable {
	private static final long serialVersionUID = 3342340508224387262L;
	@Id
	private Long levelId;
	private String levelNm;
	private String bdId;
	private Double levelPrice;
	private Integer levelMonth;
	private Double levelDiscount;
	private Integer levelEnable;
	private Long crtTs;
	private Long uptTs;
	
	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getLevelNm() {
		return levelNm;
	}

	public void setLevelNm(String levelNm) {
		this.levelNm = levelNm;
	}

	public String getBdId() {
		return bdId;
	}

	public void setBdId(String bdId) {
		this.bdId = bdId;
	}

	public Double getLevelPrice() {
		return levelPrice;
	}

	public void setLevelPrice(Double levelPrice) {
		this.levelPrice = levelPrice;
	}

	public Integer getLevelMonth() {
		return levelMonth;
	}

	public void setLevelMonth(Integer levelMonth) {
		this.levelMonth = levelMonth;
	}

	public Double getLevelDiscount() {
		return levelDiscount;
	}

	public void setLevelDiscount(Double levelDiscount) {
		this.levelDiscount = levelDiscount;
	}

	public Integer getLevelEnable() {
		return levelEnable;
	}

	public void setLevelEnable(Integer levelEnable) {
		this.levelEnable = levelEnable;
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

	public SlLevel(){
		super();
	}

	public SlLevel(Long levelId, String levelNm, String bdId,
			Double levelPrice, Integer levelMonth, Double levelDiscount,
			Integer levelEnable, Long crtTs, Long uptTs) {
		super();
		this.levelId = levelId;
		this.levelNm = levelNm;
		this.bdId = bdId;
		this.levelPrice = levelPrice;
		this.levelMonth = levelMonth;
		this.levelDiscount = levelDiscount;
		this.levelEnable = levelEnable;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
	public String duration(){
		switch(this.levelMonth){
			case 6:
				return "半年";
			case 12:
				return "1年";
			case 24:
				return "2年";
			case 36:
				return "3年";
			default:
				return this.levelMonth + "个月";
		}
	}
}
