package com.sl.api.salon.model;

import java.io.Serializable;

import com.sl.common.model.db.SlShopHoliday;

public class ShopHoliday implements Serializable {
	private static final long serialVersionUID = 5972192794575485232L;
	
	private Long stm;
	private Long etm;
	public Long getStm() {
		return stm;
	}
	public void setStm(Long stm) {
		this.stm = stm;
	}
	public Long getEtm() {
		return etm;
	}
	public void setEtm(Long etm) {
		this.etm = etm;
	}
	
	public ShopHoliday(){
		super();
	}
	
	public ShopHoliday(Long stm, Long etm) {
		super();
		this.stm = stm;
		this.etm = etm;
	}
	
	public ShopHoliday(SlShopHoliday holiday) {
		super();
		this.stm = holiday.getSofStm();
		this.etm = holiday.getSofEtm();
	}
}
