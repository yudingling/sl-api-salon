package com.sl.api.salon.model;

public enum PayTradeType {
	ORDER(0),
	LEVEL_UP(1),
	SHOP_FEE(2);
	
	private int value;
	
	private PayTradeType(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public static PayTradeType valueOf(int value){
		switch(value){
			case 0: return ORDER;
			case 1: return LEVEL_UP;
			case 2: return SHOP_FEE;
			default: throw new IllegalArgumentException(String.format("[%d] is not a kind of PayTradeType", value));
		}
	}
}
