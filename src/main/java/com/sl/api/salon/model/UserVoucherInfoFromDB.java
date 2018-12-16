package com.sl.api.salon.model;

import java.io.Serializable;

public class UserVoucherInfoFromDB implements Serializable {
	private static final long serialVersionUID = 7728747295362767923L;
	
	private Long uvId;
	private Double uvAmount;
	private Long uvStm;
	private Long uvEtm;
	private Long pjId;
	private String pjNm;
	
	public Long getUvId() {
		return uvId;
	}
	public void setUvId(Long uvId) {
		this.uvId = uvId;
	}
	public Double getUvAmount() {
		return uvAmount;
	}
	public void setUvAmount(Double uvAmount) {
		this.uvAmount = uvAmount;
	}
	public Long getUvStm() {
		return uvStm;
	}
	public void setUvStm(Long uvStm) {
		this.uvStm = uvStm;
	}
	public Long getUvEtm() {
		return uvEtm;
	}
	public void setUvEtm(Long uvEtm) {
		this.uvEtm = uvEtm;
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
	
	public UserVoucherInfoFromDB(){
		super();
	}
	
	public UserVoucherInfoFromDB(Long uvId, Double uvAmount, Long uvStm,
			Long uvEtm, Long pjId, String pjNm) {
		super();
		this.uvId = uvId;
		this.uvAmount = uvAmount;
		this.uvStm = uvStm;
		this.uvEtm = uvEtm;
		this.pjId = pjId;
		this.pjNm = pjNm;
	}
	
}
