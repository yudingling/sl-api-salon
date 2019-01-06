package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.List;

import com.sl.common.model.db.SlReservation;

public class ReservationInfo implements Serializable {
	private static final long serialVersionUID = -4731815169443542236L;
	
	private Long rvId;
	private Long shopId;
	private Long rvUid;
	private BarberInfo barber;
	private BarberProject project;
	private List<ProductInfo> products;
	private Long rvStm;
	private Long rvEtm;
	
	public Long getRvId() {
		return rvId;
	}
	public void setRvId(Long rvId) {
		this.rvId = rvId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getRvUid() {
		return rvUid;
	}
	public void setRvUid(Long rvUid) {
		this.rvUid = rvUid;
	}
	public BarberInfo getBarber() {
		return barber;
	}
	public void setBarber(BarberInfo barber) {
		this.barber = barber;
	}
	public BarberProject getProject() {
		return project;
	}
	public void setProject(BarberProject project) {
		this.project = project;
	}
	public List<ProductInfo> getProducts() {
		return products;
	}
	public void setProducts(List<ProductInfo> products) {
		this.products = products;
	}
	public Long getRvStm() {
		return rvStm;
	}
	public void setRvStm(Long rvStm) {
		this.rvStm = rvStm;
	}
	public Long getRvEtm() {
		return rvEtm;
	}
	public void setRvEtm(Long rvEtm) {
		this.rvEtm = rvEtm;
	}
	
	public ReservationInfo(){
		super();
	}
	
	public ReservationInfo(Long rvId, Long shopId, Long rvUid,
			BarberInfo barber, BarberProject project,
			List<ProductInfo> products, Long rvStm, Long rvEtm) {
		super();
		this.rvId = rvId;
		this.shopId = shopId;
		this.rvUid = rvUid;
		this.barber = barber;
		this.project = project;
		this.products = products;
		this.rvStm = rvStm;
		this.rvEtm = rvEtm;
	}
	
	public ReservationInfo(SlReservation reservation) {
		super();
		this.rvId = reservation.getRvId();
		this.shopId = reservation.getShopId();
		this.rvUid = reservation.getRvUid();
		this.rvStm = reservation.getRvStm();
		this.rvEtm = reservation.getRvEtm();
	}
	
}
