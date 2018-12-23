package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.List;

import com.sl.api.salon.model.db.SlOrder;

public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 5236094613690348130L;
	
	private Long odId;
	private Long shopId;
	private Long odUid;
	private BarberInfo barber;
	private BarberProject project;
	private List<ProductInfo> products;
	private Long odStm;
	private Long odEtm;
	private Double odPjPrice;
	private Double odPdPrice;
	private Double odTotalPrice;
	private Double odDiscount;
	private Double odOfferPrice;
	private Double odVoucherPrice;
	private Double odPayPrice;
	private Integer odPaied;
	private Long odPaiedTs;
	private String odPaiedTp;
	private Integer odComplaint;
	
	public Long getOdId() {
		return odId;
	}
	public void setOdId(Long odId) {
		this.odId = odId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getOdUid() {
		return odUid;
	}
	public void setOdUid(Long odUid) {
		this.odUid = odUid;
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
	public Long getOdStm() {
		return odStm;
	}
	public void setOdStm(Long odStm) {
		this.odStm = odStm;
	}
	public Long getOdEtm() {
		return odEtm;
	}
	public void setOdEtm(Long odEtm) {
		this.odEtm = odEtm;
	}
	public Double getOdPjPrice() {
		return odPjPrice;
	}
	public void setOdPjPrice(Double odPjPrice) {
		this.odPjPrice = odPjPrice;
	}
	public Double getOdPdPrice() {
		return odPdPrice;
	}
	public void setOdPdPrice(Double odPdPrice) {
		this.odPdPrice = odPdPrice;
	}
	public Double getOdTotalPrice() {
		return odTotalPrice;
	}
	public void setOdTotalPrice(Double odTotalPrice) {
		this.odTotalPrice = odTotalPrice;
	}
	public Double getOdDiscount() {
		return odDiscount;
	}
	public void setOdDiscount(Double odDiscount) {
		this.odDiscount = odDiscount;
	}
	public Double getOdOfferPrice() {
		return odOfferPrice;
	}
	public void setOdOfferPrice(Double odOfferPrice) {
		this.odOfferPrice = odOfferPrice;
	}
	public Double getOdVoucherPrice() {
		return odVoucherPrice;
	}
	public void setOdVoucherPrice(Double odVoucherPrice) {
		this.odVoucherPrice = odVoucherPrice;
	}
	public Double getOdPayPrice() {
		return odPayPrice;
	}
	public void setOdPayPrice(Double odPayPrice) {
		this.odPayPrice = odPayPrice;
	}
	public Integer getOdPaied() {
		return odPaied;
	}
	public void setOdPaied(Integer odPaied) {
		this.odPaied = odPaied;
	}
	public Long getOdPaiedTs() {
		return odPaiedTs;
	}
	public void setOdPaiedTs(Long odPaiedTs) {
		this.odPaiedTs = odPaiedTs;
	}
	public String getOdPaiedTp() {
		return odPaiedTp;
	}
	public void setOdPaiedTp(String odPaiedTp) {
		this.odPaiedTp = odPaiedTp;
	}
	public Integer getOdComplaint() {
		return odComplaint;
	}
	public void setOdComplaint(Integer odComplaint) {
		this.odComplaint = odComplaint;
	}
	
	public OrderInfo(){
		super();
	}
	
	public OrderInfo(Long odId, Long shopId, Long odUid, BarberInfo barber,
			BarberProject project, List<ProductInfo> products, Long odStm,
			Long odEtm, Double odPjPrice, Double odPdPrice,
			Double odTotalPrice, Double odDiscount, Double odOfferPrice,
			Double odVoucherPrice, Double odPayPrice, Integer odPaied,
			Long odPaiedTs, String odPaiedTp, Integer odComplaint) {
		super();
		this.odId = odId;
		this.shopId = shopId;
		this.odUid = odUid;
		this.barber = barber;
		this.project = project;
		this.products = products;
		this.odStm = odStm;
		this.odEtm = odEtm;
		this.odPjPrice = odPjPrice;
		this.odPdPrice = odPdPrice;
		this.odTotalPrice = odTotalPrice;
		this.odDiscount = odDiscount;
		this.odOfferPrice = odOfferPrice;
		this.odVoucherPrice = odVoucherPrice;
		this.odPayPrice = odPayPrice;
		this.odPaied = odPaied;
		this.odPaiedTs = odPaiedTs;
		this.odPaiedTp = odPaiedTp;
		this.odComplaint = odComplaint;
	}
	
	public OrderInfo(SlOrder order) {
		super();
		this.odId = order.getOdId();
		this.shopId = order.getShopId();
		this.odUid = order.getOdUid();
		this.odStm = order.getOdStm();
		this.odEtm = order.getOdEtm();
		this.odPjPrice = order.getOdPjPrice();
		this.odPdPrice = order.getOdPdPrice();
		this.odTotalPrice = order.getOdTotalPrice();
		this.odDiscount = order.getOdDiscount();
		this.odOfferPrice = order.getOdOfferPrice();
		this.odVoucherPrice = order.getOdVoucherPrice();
		this.odPayPrice = order.getOdPayPrice();
		this.odPaied = order.getOdPaied();
		this.odPaiedTs = order.getOdPaiedTs();
		this.odPaiedTp = order.getOdPaiedTp();
		this.odComplaint = order.getOdComplaint();
	}
}
