package com.sl.api.salon.model;

import java.io.Serializable;

public class UserLevelInfo implements Serializable {
	private static final long serialVersionUID = -6846213926649135230L;
	
	private Long levelId;
	private String levelNm;
	private Double ulDiscount;
	
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
	public Double getUlDiscount() {
		return ulDiscount;
	}
	public void setUlDiscount(Double ulDiscount) {
		this.ulDiscount = ulDiscount;
	}
	
	public UserLevelInfo(){
		super();
	}
	
	public UserLevelInfo(Long levelId, String levelNm, Double ulDiscount) {
		super();
		this.levelId = levelId;
		this.levelNm = levelNm;
		this.ulDiscount = ulDiscount;
	}
	
}
