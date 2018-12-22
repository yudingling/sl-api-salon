package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlProject implements Serializable {
	private static final long serialVersionUID = -7593412141055354567L;
	@Id
	private Long pjId;
	private String pjNm;
	private String bdId;
	private Integer pjEnable;
	private Double pjPrice;
	private Double pjHour;
	private Long crtTs;
	private Long uptTs;
	
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
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public Integer getPjEnable() {
		return pjEnable;
	}
	public void setPjEnable(Integer pjEnable) {
		this.pjEnable = pjEnable;
	}
	public Double getPjPrice() {
		return pjPrice;
	}
	public void setPjPrice(Double pjPrice) {
		this.pjPrice = pjPrice;
	}
	public Double getPjHour() {
		return pjHour;
	}
	public void setPjHour(Double pjHour) {
		this.pjHour = pjHour;
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
	
	public SlProject(){
		super();
	}
	
	public SlProject(Long pjId, String pjNm, String bdId, Integer pjEnable,
			Double pjPrice, Double pjHour, Long crtTs, Long uptTs) {
		super();
		this.pjId = pjId;
		this.pjNm = pjNm;
		this.bdId = bdId;
		this.pjEnable = pjEnable;
		this.pjPrice = pjPrice;
		this.pjHour = pjHour;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
