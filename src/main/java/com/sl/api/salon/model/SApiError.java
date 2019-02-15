package com.sl.api.salon.model;

public class SApiError {
	private SApiError(){}
	
	public static final int UNAUTHORIZED = 2001;
	public static final int GETPHONE_FAILED = 2002;
	public static final int USER_UNEXISTS = 2003;
	public static final int WECHAT_VERIFY_FAILED = 2004;
	
	public static final int ORDER_UNPAIED = 3001;
	public static final int RESERVATION_TIME_SHILED = 3002;
	public static final int ORDER_CONFIRM_FAILED = 3003;
	public static final int ORDER_CANCEL_FAILED = 3004;
	public static final int ORDER_PAID_FAILED = 3005;
	
	public static final int SHOP_OUT_OF_SERVICE = 4001;
}
