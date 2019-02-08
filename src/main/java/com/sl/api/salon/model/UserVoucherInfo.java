package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserVoucherInfo implements Serializable {
	private static final long serialVersionUID = 4366719795636249617L;
	
	private Long uvId;
	private Double uvAmount;
	private Long uvStm;
	private Long uvEtm;
	private Map<Long, String> projects;
	
	public Long getUvId() {
		return uvId;
	}
	public void setUvId(Long uvId) {
		this.uvId = uvId;
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
	public Map<Long, String> getProjects() {
		return projects;
	}
	public void setProjects(Map<Long, String> projects) {
		this.projects = projects;
	}
	
	public UserVoucherInfo(){
		super();
	}
	
	public UserVoucherInfo(Long uvId, Double uvAmount, Long uvStm, Long uvEtm,
			Map<Long, String> projects) {
		super();
		this.uvId = uvId;
		this.uvAmount = uvAmount;
		this.uvStm = uvStm;
		this.uvEtm = uvEtm;
		this.projects = projects;
	}
	
	public UserVoucherInfo(UserVoucherInfoFromDB info) {
		super();
		this.uvId = info.getUvId();
		this.uvAmount = info.getUvAmount();
		this.uvStm = info.getUvStm();
		this.uvEtm = info.getUvEtm();
		this.projects = new HashMap<>();
	}
	
	public void addProject(UserVoucherInfoFromDB info){
		String pjNm = info.getPjId() == 0l ? "all projects" : info.getPjNm();
		this.projects.put(info.getPjId(), pjNm);
	}
}
