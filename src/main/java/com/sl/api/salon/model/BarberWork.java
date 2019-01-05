package com.sl.api.salon.model;

import java.io.Serializable;

public class BarberWork implements Serializable {
	private static final long serialVersionUID = -2379697918845194886L;
	
	private Long bbwId;
	private Long uId;
	private String bbwTitle;
	private String bbwImg;
	private Long bbwLike;
	private Boolean liked;
	
	public Long getBbwId() {
		return bbwId;
	}
	public void setBbwId(Long bbwId) {
		this.bbwId = bbwId;
	}
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public String getBbwTitle() {
		return bbwTitle;
	}
	public void setBbwTitle(String bbwTitle) {
		this.bbwTitle = bbwTitle;
	}
	public String getBbwImg() {
		return bbwImg;
	}
	public void setBbwImg(String bbwImg) {
		this.bbwImg = bbwImg;
	}
	public Long getBbwLike() {
		return bbwLike;
	}
	public void setBbwLike(Long bbwLike) {
		this.bbwLike = bbwLike;
	}
	public Boolean getLiked() {
		return liked;
	}
	public void setLiked(Boolean liked) {
		this.liked = liked;
	}
	
	public BarberWork(){
		super();
	}
	
	public BarberWork(Long bbwId, Long uId, String bbwTitle, String bbwImg,
			Long bbwLike, Boolean liked) {
		super();
		this.bbwId = bbwId;
		this.uId = uId;
		this.bbwTitle = bbwTitle;
		this.bbwImg = bbwImg;
		this.bbwLike = bbwLike;
		this.liked = liked;
	}
	
	public BarberWork(BarberWorkExt work, String bbwImg) {
		super();
		this.bbwId = work.getBbwId();
		this.uId = work.getuId();
		this.bbwTitle = work.getBbwTitle();
		this.bbwLike = work.getBbwLike();
		this.liked = work.getBbwlId() != null;
		
		this.bbwImg = bbwImg;
	}
}
