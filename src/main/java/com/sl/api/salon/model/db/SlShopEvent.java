package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlShopEvent implements Serializable {
	private static final long serialVersionUID = 5943593761041418473L;
	@Id
	private Long eventId;
	private Long eventNm;
	private String bdId;
	private Long shopId;
	private Long eventImg;
	private String eventUrl;
	private Long eventStm;
	private Long eventEtm;
	private Integer eventAvailable;
	private Long crtTs;
	private Long uptTs;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Long getEventNm() {
		return eventNm;
	}
	public void setEventNm(Long eventNm) {
		this.eventNm = eventNm;
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getEventImg() {
		return eventImg;
	}
	public void setEventImg(Long eventImg) {
		this.eventImg = eventImg;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	public Long getEventStm() {
		return eventStm;
	}
	public void setEventStm(Long eventStm) {
		this.eventStm = eventStm;
	}
	public Long getEventEtm() {
		return eventEtm;
	}
	public void setEventEtm(Long eventEtm) {
		this.eventEtm = eventEtm;
	}
	public Integer getEventAvailable() {
		return eventAvailable;
	}
	public void setEventAvailable(Integer eventAvailable) {
		this.eventAvailable = eventAvailable;
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
	
	public SlShopEvent(){
		super();
	}
	
	public SlShopEvent(Long eventId, Long eventNm, String bdId, Long shopId,
			Long eventImg, String eventUrl, Long eventStm, Long eventEtm,
			Integer eventAvailable, Long crtTs, Long uptTs) {
		super();
		this.eventId = eventId;
		this.eventNm = eventNm;
		this.bdId = bdId;
		this.shopId = shopId;
		this.eventImg = eventImg;
		this.eventUrl = eventUrl;
		this.eventStm = eventStm;
		this.eventEtm = eventEtm;
		this.eventAvailable = eventAvailable;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
