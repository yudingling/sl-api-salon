package com.sl.api.salon.util;

public class Constant {
	private Constant(){}
	
	/**
	 * request parameter id
	 */
	public static final String REQUEST_PARAM_TOKEN = "token";
	
	/**
	 * redis prefix
	 */
	private static final String REDIS_PREFIX = "salon:";
	public static final String REDIS_PREFIX_TOKEN = REDIS_PREFIX + "tk_";
}
