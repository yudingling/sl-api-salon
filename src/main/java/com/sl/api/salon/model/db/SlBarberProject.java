package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlBarberProject implements Serializable {
	private static final long serialVersionUID = -6219352931686096504L;
	@Id
	private Long bbpId;
	private Long uId;
	private Long pjId;
	private Double bbpPrice;
	private Long crtTs;
	private Long uptTs;
	
	public Long getBbpId() {
		return bbpId;
	}
	public void setBbpId(Long bbpId) {
		this.bbpId = bbpId;
	}
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public Long getPjId() {
		return pjId;
	}
	public void setPjId(Long pjId) {
		this.pjId = pjId;
	}
	public Double getBbpPrice() {
		return bbpPrice;
	}
	public void setBbpPrice(Double bbpPrice) {
		this.bbpPrice = bbpPrice;
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
	
	public SlBarberProject(){
		super();
	}
	
	public SlBarberProject(Long bbpId, Long uId, Long pjId, Double bbpPrice,
			Long crtTs, Long uptTs) {
		super();
		this.bbpId = bbpId;
		this.uId = uId;
		this.pjId = pjId;
		this.bbpPrice = bbpPrice;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
