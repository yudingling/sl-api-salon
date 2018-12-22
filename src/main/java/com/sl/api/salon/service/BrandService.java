package com.sl.api.salon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.sl.api.salon.mapper.SlBrandMapper;
import com.sl.api.salon.mapper.SlShopHolidayMapper;
import com.sl.api.salon.mapper.SlShopMapper;
import com.sl.api.salon.model.BrandInfo;
import com.sl.api.salon.model.SToken;
import com.sl.api.salon.model.db.SlBrand;
import com.sl.api.salon.model.db.SlShop;
import com.sl.api.salon.model.db.SlShopHoliday;

@Service
public class BrandService {
	@Autowired
	private SlBrandMapper slBrandMapper;
	@Autowired
	private SlShopMapper slShopMapper;
	@Autowired
	private SlShopHolidayMapper slShopHolidayMapper;
	
	public BrandInfo getBrandInfo(SToken token, double lgtd, double lttd){
		SlBrand brand = this.slBrandMapper.selectByPrimaryKey(token.getBrandId());
		
		List<SlShop> shops = this.getShops(token.getBrandId());
		Map<Long, List<SlShopHoliday>> holidayMap = this.getShopHolidays(shops);
		
		return new BrandInfo(brand, shops, holidayMap, lgtd, lttd);
	}
	
	private List<SlShop> getShops(String bdId){
		Example example = new Example(SlShop.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andEqualTo("shopEnable", 1);
	    example.orderBy("crtTs").asc();
	    
	    return this.slShopMapper.selectByExample(example);
	}
	
	private Map<Long, List<SlShopHoliday>> getShopHolidays(List<SlShop> shops){
		Set<Long> shopIds = shops.stream().map(SlShop::getShopId).collect(Collectors.toSet());
		
		Example example = new Example(SlShopHoliday.class);
	    example.createCriteria().andIn("shopId", shopIds).andGreaterThan("sofEtm", System.currentTimeMillis());
	    
	    List<SlShopHoliday> holidays = this.slShopHolidayMapper.selectByExample(example);
	    
	    Map<Long, List<SlShopHoliday>> holidayMap = new HashMap<>();
	    
		if(CollectionUtils.isNotEmpty(holidays)){
			for(SlShopHoliday holiday : holidays){
				List<SlShopHoliday> tmp = holidayMap.get(holiday.getShopId());
				if(tmp == null){
					tmp = new ArrayList<>();
					holidayMap.put(holiday.getShopId(), tmp);
				}
				
				tmp.add(holiday);
			}
		}
		
		return holidayMap;
	}
}
