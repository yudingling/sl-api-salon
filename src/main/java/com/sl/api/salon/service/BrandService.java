package com.sl.api.salon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.sl.api.salon.mapper.SlBrandMapper;
import com.sl.api.salon.mapper.SlProductMapper;
import com.sl.api.salon.mapper.SlShopEventMapper;
import com.sl.api.salon.mapper.SlShopHolidayMapper;
import com.sl.api.salon.mapper.SlShopImageMapper;
import com.sl.api.salon.mapper.SlShopMapper;
import com.sl.api.salon.model.BrandInfo;
import com.sl.api.salon.model.ProductInfo;
import com.sl.api.salon.model.ShopEvent;
import com.sl.api.salon.model.ShopHoliday;
import com.sl.common.model.SToken;
import com.sl.common.model.db.SlBrand;
import com.sl.common.model.db.SlProduct;
import com.sl.common.model.db.SlShop;
import com.sl.common.model.db.SlShopEvent;
import com.sl.common.model.db.SlShopHoliday;
import com.sl.common.model.db.SlShopImage;

@Service
public class BrandService {
	@Autowired
	private SlBrandMapper slBrandMapper;
	@Autowired
	private SlShopMapper slShopMapper;
	@Autowired
	private SlShopHolidayMapper slShopHolidayMapper;
	@Autowired
	private SlShopImageMapper slShopImageMapper;
	@Autowired
	private SlShopEventMapper slShopEventMapper;
	@Autowired
	private SlProductMapper slProductMapper;
	@Autowired
	private CommonService commonService;
	
	public BrandInfo getBrandInfo(SToken token, double lgtd, double lttd){
		String bdId = token.getBrandId();
		
		SlBrand brand = this.slBrandMapper.selectByPrimaryKey(bdId);
		
		List<SlShop> shops = this.getShops(bdId);
		Map<Long, List<ShopHoliday>> holidayMap = this.getShopHolidays(bdId);
		Map<Long, List<String>> imageMap = this.getShopImages(bdId);
		Map<Long, ShopEvent> eventMap = this.getShopEvents(bdId);
		
		String brandLogo = this.commonService.getIconUrl(brand);
		
		return new BrandInfo(brand, brandLogo, shops, holidayMap, imageMap, eventMap, this.getProductInfo(token), lgtd, lttd);
	}
	
	private List<SlShop> getShops(String bdId){
		Example example = new Example(SlShop.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andEqualTo("shopEnable", 1);
	    example.orderBy("crtTs").asc();
	    
	    return this.slShopMapper.selectByExample(example);
	}
	
	private Map<Long, ProductInfo> getProductInfo(SToken token){
		Map<Long, ProductInfo> productMap = new HashMap<>();
		
		List<SlProduct> products = this.getAllProducts(token);
		if(CollectionUtils.isNotEmpty(products)){
			for(SlProduct pd : products){
				String iconUrl = this.commonService.getIconUrl(pd);
				
				productMap.put(pd.getPdId(), new ProductInfo(pd, iconUrl));
			}
		}
		
		return productMap;
	}
	
	private Map<Long, List<String>> getShopImages(String bdId){
		Example example = new Example(SlShopImage.class);
	    example.createCriteria().andEqualTo("bdId", bdId);
	    example.orderBy("crtTs").asc();
	    
	    List<SlShopImage> images = this.slShopImageMapper.selectByExample(example);
	    Map<Long, List<String>> imageMap = new HashMap<>();
	    
	    if(CollectionUtils.isNotEmpty(images)){
			for(SlShopImage img : images){
				List<String> tmp = imageMap.get(img.getShopId());
				if(tmp == null){
					tmp = new ArrayList<>();
					imageMap.put(img.getShopId(), tmp);
				}
				
				String imageUrl = this.commonService.getIconUrl(img);
				if(StringUtils.isNotEmpty(imageUrl)){
					tmp.add(imageUrl);
				}
			}
		}
		
		return imageMap;
	}
	
	private Map<Long, ShopEvent> getShopEvents(String bdId){
		Example example = new Example(SlShopEvent.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andEqualTo("eventAvailable", 1).andGreaterThan("eventEtm", System.currentTimeMillis());
	    example.orderBy("crtTs").desc();
	    
	    List<SlShopEvent> events = this.slShopEventMapper.selectByExample(example);
	    Map<Long, ShopEvent> eventMap = new HashMap<>();
	    
	    if(CollectionUtils.isNotEmpty(events)){
			for(SlShopEvent event : events){
				if(!eventMap.containsKey(event.getShopId())){
					String imageUrl = this.commonService.getIconUrl(event);
					if(StringUtils.isNotEmpty(imageUrl)){
						eventMap.put(event.getShopId(), new ShopEvent(imageUrl, event.getEventUrl()));
					}
				}
			}
		}
		
		return eventMap;
	}
	
	private Map<Long, List<ShopHoliday>> getShopHolidays(String bdId){
		Example example = new Example(SlShopHoliday.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andGreaterThan("sofEtm", System.currentTimeMillis());
	    example.orderBy("sofStm").asc();
	    
	    List<SlShopHoliday> holidays = this.slShopHolidayMapper.selectByExample(example);
	    
	    Map<Long, List<ShopHoliday>> holidayMap = new HashMap<>();
	    
		if(CollectionUtils.isNotEmpty(holidays)){
			for(SlShopHoliday holiday : holidays){
				List<ShopHoliday> tmp = holidayMap.get(holiday.getShopId());
				if(tmp == null){
					tmp = new ArrayList<>();
					holidayMap.put(holiday.getShopId(), tmp);
				}
				
				tmp.add(new ShopHoliday(holiday));
			}
		}
		
		return holidayMap;
	}
	
	private List<SlProduct> getAllProducts(SToken token){
		Example example = new Example(SlProduct.class);
	    example.createCriteria().andEqualTo("bdId", token.getBrandId()).andEqualTo("pdEnable", 1);
	    example.orderBy("pdPrice").desc();
	    
	    return this.slProductMapper.selectByExample(example);
	}
}
