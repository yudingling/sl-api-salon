package com.sl.api.salon.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlRoleApi implements Serializable {
	private static final long serialVersionUID = -2808497850513753912L;
	@Id
	private Long roaId;
	private String roleId;
	private String apiUrl;
	private Integer httpGet;
	private Integer httpPost;
	private Integer httpPut;
	private Integer httpDelete;
	private Long crtTs;
	private Long uptTs;
	
	public Long getRoaId() {
		return roaId;
	}
	public void setRoaId(Long roaId) {
		this.roaId = roaId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public int getHttpGet() {
		return httpGet;
	}
	public void setHttpGet(int httpGet) {
		this.httpGet = httpGet;
	}
	public int getHttpPost() {
		return httpPost;
	}
	public void setHttpPost(int httpPost) {
		this.httpPost = httpPost;
	}
	public int getHttpPut() {
		return httpPut;
	}
	public void setHttpPut(int httpPut) {
		this.httpPut = httpPut;
	}
	public int getHttpDelete() {
		return httpDelete;
	}
	public void setHttpDelete(int httpDelete) {
		this.httpDelete = httpDelete;
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
	
	public SlRoleApi(){
		super();
	}
	
	public SlRoleApi(Long roaId, String roleId, String apiUrl, int httpGet,
			int httpPost, int httpPut, int httpDelete, Long crtTs, Long uptTs) {
		super();
		this.roaId = roaId;
		this.roleId = roleId;
		this.apiUrl = apiUrl;
		this.httpGet = httpGet;
		this.httpPost = httpPost;
		this.httpPut = httpPut;
		this.httpDelete = httpDelete;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
