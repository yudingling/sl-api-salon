package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlUserVoucher implements Serializable {
	private static final long serialVersionUID = -1352944858487388097L;
	@Id
	private Long uvId;
	private Long uId;
	private String bdId;
	private Double uvAmount;
	private Long uvStm;
	private Long uvEtm;
	private String uvDuration;
	private Integer uvAvailable;
	private Long  odId;
	private Long crtTs;
	private Long uptTs;
	
	public Long getUvId() {
		return uvId;
	}

	public void setUvId(Long uvId) {
		this.uvId = uvId;
	}

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public String getBdId() {
		return bdId;
	}

	public void setBdId(String bdId) {
		this.bdId = bdId;
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

	public String getUvDuration() {
		return uvDuration;
	}

	public void setUvDuration(String uvDuration) {
		this.uvDuration = uvDuration;
	}

	public Integer getUvAvailable() {
		return uvAvailable;
	}

	public void setUvAvailable(Integer uvAvailable) {
		this.uvAvailable = uvAvailable;
	}

	public Long getOdId() {
		return odId;
	}

	public void setOdId(Long odId) {
		this.odId = odId;
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

	public SlUserVoucher(){
		super();
	}

	public SlUserVoucher(Long uvId, Long uId, String bdId, Double uvAmount,
			Long uvStm, Long uvEtm, String uvDuration, Integer uvAvailable,
			Long odId, Long crtTs, Long uptTs) {
		super();
		this.uvId = uvId;
		this.uId = uId;
		this.bdId = bdId;
		this.uvAmount = uvAmount;
		this.uvStm = uvStm;
		this.uvEtm = uvEtm;
		this.uvDuration = uvDuration;
		this.uvAvailable = uvAvailable;
		this.odId = odId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
}
