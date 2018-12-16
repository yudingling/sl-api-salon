package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.List;

public class MemberInfo implements Serializable {
	private static final long serialVersionUID = 5794946085391693823L;
	
	private Long uId;
	private String uNm;
	private String uIcon;
	private UserLevelInfo levelInfo;
	private List<UserVoucherInfo> vouchers;
	
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
	
	public MemberInfo(){
		super();
	}
	
	public MemberInfo(Long uId, String uNm, String uIcon,
			UserLevelInfo levelInfo, List<UserVoucherInfo> vouchers) {
		super();
		this.uId = uId;
		this.uNm = uNm;
		this.uIcon = uIcon;
		this.levelInfo = levelInfo;
		this.vouchers = vouchers;
	}
	
}