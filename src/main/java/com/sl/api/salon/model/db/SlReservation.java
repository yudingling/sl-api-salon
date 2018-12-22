package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlReservation implements Serializable {
	private static final long serialVersionUID = 5719241670450842950L;
	@Id
	private Long rvId;
	private Long shopId;
	private Long rvUid;
	private Long rvBarberUid;
	private Long pjId;
	private Long rvStm;
	private Long rvEtm;
	private Integer rvAvailable;
	private Integer rvActive;
	private Integer rvIssystem;
	private Long crtTs;
	private Long uptTs;
	
	public Long getRvId() {
		return rvId;
	}
	public void setRvId(Long rvId) {
		this.rvId = rvId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getRvUid() {
		return rvUid;
	}
	public void setRvUid(Long rvUid) {
		this.rvUid = rvUid;
	}
	public Long getRvBarberUid() {
		return rvBarberUid;
	}
	public void setRvBarberUid(Long rvBarberUid) {
		this.rvBarberUid = rvBarberUid;
	}
	public Long getPjId() {
		return pjId;
	}
	public void setPjId(Long pjId) {
		this.pjId = pjId;
	}
	public Long getRvStm() {
		return rvStm;
	}
	public void setRvStm(Long rvStm) {
		this.rvStm = rvStm;
	}
	public Long getRvEtm() {
		return rvEtm;
	}
	public void setRvEtm(Long rvEtm) {
		this.rvEtm = rvEtm;
	}
	public Integer getRvAvailable() {
		return rvAvailable;
	}
	public void setRvAvailable(Integer rvAvailable) {
		this.rvAvailable = rvAvailable;
	}
	public Integer getRvActive() {
		return rvActive;
	}
	public void setRvActive(Integer rvActive) {
		this.rvActive = rvActive;
	}
	public Integer getRvIssystem() {
		return rvIssystem;
	}
	public void setRvIssystem(Integer rvIssystem) {
		this.rvIssystem = rvIssystem;
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
	
	public SlReservation(){
		super();
	}
	
	public SlReservation(Long rvId, Long shopId, Long rvUid, Long rvBarberUid,
			Long pjId, Long rvStm, Long rvEtm, Integer rvAvailable,
			Integer rvActive, Integer rvIssystem, Long crtTs, Long uptTs) {
		super();
		this.rvId = rvId;
		this.shopId = shopId;
		this.rvUid = rvUid;
		this.rvBarberUid = rvBarberUid;
		this.pjId = pjId;
		this.rvStm = rvStm;
		this.rvEtm = rvEtm;
		this.rvAvailable = rvAvailable;
		this.rvActive = rvActive;
		this.rvIssystem = rvIssystem;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
