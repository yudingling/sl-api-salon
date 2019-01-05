package com.sl.api.salon.model;

import java.io.Serializable;

public class ShopEvent implements Serializable {
	private static final long serialVersionUID = -1467653014585790745L;
	
	private String eventImg;
	private String eventUrl;
	
	public String getEventImg() {
		return eventImg;
	}
	public void setEventImg(String eventImg) {
		this.eventImg = eventImg;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	
	public ShopEvent(){
		super();
	}
	
	public ShopEvent(String eventImg, String eventUrl) {
		super();
		this.eventImg = eventImg;
		this.eventUrl = eventUrl;
	}
	
}
