package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlBrand implements Serializable {
	private static final long serialVersionUID = 1922190879412696910L;
	@Id
	private String bdId;
	private String bdNm;
	private Long bdLogo;
	private String bdUrl;
	private Long crtTs;
	private Long uptTs;
	
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public String getBdNm() {
		return bdNm;
	}
	public void setBdNm(String bdNm) {
		this.bdNm = bdNm;
	}
	public Long getBdLogo() {
		return bdLogo;
	}
	public void setBdLogo(Long bdLogo) {
		this.bdLogo = bdLogo;
	}
	public String getBdUrl() {
		return bdUrl;
	}
	public void setBdUrl(String bdUrl) {
		this.bdUrl = bdUrl;
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
	
	public SlBrand(){
		super();
	}
	
	public SlBrand(String bdId, String bdNm, Long bdLogo, String bdUrl,
			Long crtTs, Long uptTs) {
		super();
		this.bdId = bdId;
		this.bdNm = bdNm;
		this.bdLogo = bdLogo;
		this.bdUrl = bdUrl;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
