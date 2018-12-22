package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarberReservation implements Serializable {
	private static final long serialVersionUID = -4890646181704920031L;
	
	private List<BarberInfo> bbInfo;
	private Map<Long, ProductInfo> pdInfo;
	
	public List<BarberInfo> getBbInfo() {
		return bbInfo;
	}

	public void setBbInfo(List<BarberInfo> bbInfo) {
		this.bbInfo = bbInfo;
	}

	public Map<Long, ProductInfo> getPdInfo() {
		return pdInfo;
	}

	public void setPdInfo(Map<Long, ProductInfo> pdInfo) {
		this.pdInfo = pdInfo;
	}

	public BarberReservation(){
		super();
		this.bbInfo = new ArrayList<>();
		this.pdInfo = new HashMap<>();
	}
	
	public BarberReservation(List<BarberInfo> bbInfo,
			Map<Long, ProductInfo> pdInfo) {
		super();
		this.bbInfo = bbInfo;
		this.pdInfo = pdInfo;
	}
}
