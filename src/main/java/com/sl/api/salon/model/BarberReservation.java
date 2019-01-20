package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BarberReservation implements Serializable {
	private static final long serialVersionUID = -4890646181704920031L;
	
	private List<BarberInfo> bbInfo;
	
	public List<BarberInfo> getBbInfo() {
		return bbInfo;
	}

	public void setBbInfo(List<BarberInfo> bbInfo) {
		this.bbInfo = bbInfo;
	}

	public BarberReservation(){
		super();
		this.bbInfo = new ArrayList<>();
	}
	
	public BarberReservation(List<BarberInfo> bbInfo) {
		super();
		this.bbInfo = bbInfo;
	}
}
