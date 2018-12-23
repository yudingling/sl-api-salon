package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.Set;

import com.sl.api.salon.model.db.SlBarberProject;
import com.sl.api.salon.model.db.SlProject;

public class BarberProject implements Serializable {
	private static final long serialVersionUID = 709387429052072702L;
	
	private Long pjId;
	private String pjNm;
	private Double pjPrice;
	private Double pjHour;
	private Set<Long> pdIds;
	
	public Long getPjId() {
		return pjId;
	}
	public void setPjId(Long pjId) {
		this.pjId = pjId;
	}
	public String getPjNm() {
		return pjNm;
	}
	public void setPjNm(String pjNm) {
		this.pjNm = pjNm;
	}
	public Double getPjPrice() {
		return pjPrice;
	}
	public void setPjPrice(Double pjPrice) {
		this.pjPrice = pjPrice;
	}
	public Double getPjHour() {
		return pjHour;
	}
	public void setPjHour(Double pjHour) {
		this.pjHour = pjHour;
	}
	public Set<Long> getPdIds() {
		return pdIds;
	}
	public void setPdIds(Set<Long> pdIds) {
		this.pdIds = pdIds;
	}
	
	public BarberProject(){
		super();
	}
	
	public BarberProject(Long pjId, String pjNm, Double pjPrice, Double pjHour,
			Set<Long> pdIds) {
		super();
		this.pjId = pjId;
		this.pjNm = pjNm;
		this.pjPrice = pjPrice;
		this.pjHour = pjHour;
		this.pdIds = pdIds;
	}
	
	public BarberProject(SlProject project, Set<Long> pdIds) {
		super();
		this.pjId = project.getPjId();
		this.pjNm = project.getPjNm();
		this.pjPrice = project.getPjPrice();
		this.pjHour = project.getPjHour();
		this.pdIds = pdIds;
	}
	
	public BarberProject(SlProject project, SlBarberProject bbp, Set<Long> pdIds) {
		super();
		this.pjId = project.getPjId();
		this.pjNm = project.getPjNm();
		this.pjPrice = bbp.getBbpPrice();
		this.pjHour = project.getPjHour();
		this.pdIds = pdIds;
	}
}
