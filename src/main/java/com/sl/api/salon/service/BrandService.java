package com.sl.api.salon.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import com.sl.api.salon.mapper.SlBrandMapper;
import com.sl.api.salon.mapper.SlShopMapper;
import com.sl.api.salon.model.BrandInfo;
import com.sl.api.salon.model.SToken;
import com.sl.api.salon.model.db.SlBrand;
import com.sl.api.salon.model.db.SlShop;

@Service
public class BrandService {
	@Autowired
	private SlBrandMapper slBrandMapper;
	@Autowired
	private SlShopMapper slShopMapper;
	
	public BrandInfo getBrandInfo(SToken token, double lgtd, double lttd){
		SlBrand brand = this.slBrandMapper.selectByPrimaryKey(token.getBrandId());
		List<SlShop> shops = this.getShops(token.getBrandId());
		
		return new BrandInfo(brand, shops, lgtd, lttd);
	}
	
	private List<SlShop> getShops(String bdId){
		Example example = new Example(SlShop.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andEqualTo("shopEnable", 1);
	    example.orderBy("crtTs").asc();
	    
	    return this.slShopMapper.selectByExample(example);
	}
}
