package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlUserWechat implements Serializable {
	private static final long serialVersionUID = 2582188809985955051L;
	@Id
	private Long uwId;
	private String uwUnionid;
	private Long uId;
	private Long crtTs;
	private Long uptTs;
	
	public Long getUwId() {
		return uwId;
	}

	public void setUwId(Long uwId) {
		this.uwId = uwId;
	}

	public String getUwUnionid() {
		return uwUnionid;
	}

	public void setUwUnionid(String uwUnionid) {
		this.uwUnionid = uwUnionid;
	}

	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
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

	public SlUserWechat(){
		super();
	}

	public SlUserWechat(Long uwId, String uwUnionid, Long uId, Long crtTs,
			Long uptTs) {
		super();
		this.uwId = uwId;
		this.uwUnionid = uwUnionid;
		this.uId = uId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
}
