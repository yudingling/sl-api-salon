package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlMsg implements Serializable {
	private static final long serialVersionUID = -3928434040767928669L;
	@Id
	private Long msgId;
	private Long uId;
	private String msgTp;
	private String msgContent;
	private Integer msgReaded;
	private Long crtTs;
	private Long uptTs;
	
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public String getMsgTp() {
		return msgTp;
	}
	public void setMsgTp(String msgTp) {
		this.msgTp = msgTp;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public Integer getMsgReaded() {
		return msgReaded;
	}
	public void setMsgReaded(Integer msgReaded) {
		this.msgReaded = msgReaded;
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
	
	public SlMsg(){
		super();
	}
	
	public SlMsg(Long msgId, Long uId, String msgTp, String msgContent,
			Integer msgReaded, Long crtTs, Long uptTs) {
		super();
		this.msgId = msgId;
		this.uId = uId;
		this.msgTp = msgTp;
		this.msgContent = msgContent;
		this.msgReaded = msgReaded;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
