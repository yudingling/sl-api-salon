package com.sl.api.salon.model;

import java.io.Serializable;

public class BarberWorkExt implements Serializable {
	private static final long serialVersionUID = -7934967266280123818L;
	
	private Long bbwId;
	private Long uId;
	private String bbwTitle;
	private Long bbwImg;
	private Long bbwLike;
	private Long bbwlId;
	
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

	public Long getBbwImg() {
		return bbwImg;
	}

	public void setBbwImg(Long bbwImg) {
		this.bbwImg = bbwImg;
	}

	public Long getBbwLike() {
		return bbwLike;
	}

	public void setBbwLike(Long bbwLike) {
		this.bbwLike = bbwLike;
	}

	public Long getBbwlId() {
		return bbwlId;
	}

	public void setBbwlId(Long bbwlId) {
		this.bbwlId = bbwlId;
	}

	public BarberWorkExt(){
		super();
	}

	public BarberWorkExt(Long bbwId, Long uId, String bbwTitle, Long bbwImg,
			Long bbwLike, Long bbwlId) {
		super();
		this.bbwId = bbwId;
		this.uId = uId;
		this.bbwTitle = bbwTitle;
		this.bbwImg = bbwImg;
		this.bbwLike = bbwLike;
		this.bbwlId = bbwlId;
	}
	
}
