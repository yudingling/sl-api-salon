package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.List;

import com.sl.common.model.db.SlMsg;

public class MemberInfo implements Serializable {
	private static final long serialVersionUID = 5794946085391693823L;
	
	private Long uId;
	private String uNm;
	private String uIcon;
	private String uPhone;
	private UserLevelInfo levelInfo;
	private List<UserVoucherInfo> vouchers;
	private ReservationInfo reservation;
	private OrderInfo order;
	private List<SlMsg> latestMsgs;
	private Integer unReadMsgCount;
	
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public String getuNm() {
		return uNm;
	}
	public void setuNm(String uNm) {
		this.uNm = uNm;
	}
	public String getuIcon() {
		return uIcon;
	}
	public void setuIcon(String uIcon) {
		this.uIcon = uIcon;
	}
	public String getuPhone() {
		return uPhone;
	}
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	public UserLevelInfo getLevelInfo() {
		return levelInfo;
	}
	public void setLevelInfo(UserLevelInfo levelInfo) {
		this.levelInfo = levelInfo;
	}
	public List<UserVoucherInfo> getVouchers() {
		return vouchers;
	}
	public void setVouchers(List<UserVoucherInfo> vouchers) {
		this.vouchers = vouchers;
	}
	public ReservationInfo getReservation() {
		return reservation;
	}
	public void setReservation(ReservationInfo reservation) {
		this.reservation = reservation;
	}
	public OrderInfo getOrder() {
		return order;
	}
	public void setOrder(OrderInfo order) {
		this.order = order;
	}
	public List<SlMsg> getLatestMsgs() {
		return latestMsgs;
	}
	public void setLatestMsgs(List<SlMsg> latestMsgs) {
		this.latestMsgs = latestMsgs;
	}
	public Integer getUnReadMsgCount() {
		return unReadMsgCount;
	}
	public void setUnReadMsgCount(Integer unReadMsgCount) {
		this.unReadMsgCount = unReadMsgCount;
	}
	
	public MemberInfo(){
		super();
	}
	
	public MemberInfo(Long uId, String uNm, String uIcon, String uPhone,
			UserLevelInfo levelInfo, List<UserVoucherInfo> vouchers,
			ReservationInfo reservation, OrderInfo order,
			List<SlMsg> latestMsgs, Integer unReadMsgCount) {
		super();
		this.uId = uId;
		this.uNm = uNm;
		this.uIcon = uIcon;
		this.uPhone = uPhone;
		this.levelInfo = levelInfo;
		this.vouchers = vouchers;
		this.reservation = reservation;
		this.order = order;
		this.latestMsgs = latestMsgs;
		this.unReadMsgCount = unReadMsgCount;
	}
}
