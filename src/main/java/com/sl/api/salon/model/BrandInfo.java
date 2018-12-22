package com.sl.api.salon.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.sl.api.salon.model.db.SlBrand;
import com.sl.api.salon.model.db.SlShop;
import com.sl.api.salon.model.db.SlShopHoliday;

public class BrandInfo implements Serializable {
	private static final long serialVersionUID = -7442285215862675086L;
	
	private String bdId;
	private String bdNm;
	private String bdUrl;
	private List<ShopInfo> shops;
	private ShopInfo recommend;
	
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public String getBdNm() {
		return bdNm;
	}
	public void setBdNm(String bdNm) {
		this.bdNm = bdNm;
	}
	public String getBdUrl() {
		return bdUrl;
	}
	public void setBdUrl(String bdUrl) {
		this.bdUrl = bdUrl;
	}
	public List<ShopInfo> getShops() {
		return shops;
	}
	public void setShops(List<ShopInfo> shops) {
		this.shops = shops;
	}
	public ShopInfo getRecommend() {
		return recommend;
	}
	public void setRecommend(ShopInfo recommend) {
		this.recommend = recommend;
	}
	public BrandInfo(){
		super();
	}
	
	public BrandInfo(String bdId, String bdNm, String bdUrl,
			List<ShopInfo> shops, ShopInfo recommend) {
		super();
		this.bdId = bdId;
		this.bdNm = bdNm;
		this.bdUrl = bdUrl;
		this.shops = shops;
		this.recommend = recommend;
	}
	
	public BrandInfo(SlBrand brand, List<SlShop> shops, Map<Long, List<SlShopHoliday>> holidayMap, double lgtd, double lttd) {
		super();
		this.bdId = brand.getBdId();
		this.bdNm = brand.getBdNm();
		this.bdUrl = brand.getBdUrl();
		this.shops = new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(shops)){
			List<DistanceCmp> distance = new ArrayList<>();
			
			for(SlShop item : shops){
				ShopInfo info = new ShopInfo(item, holidayMap.get(item.getShopId()));
				this.shops.add(info);
				
				distance.add(new DistanceCmp(info, CalcDistance.getDistance(lgtd, lttd, item.getShopLgtd(), item.getShopLttd())));
			}
			
			distance.sort((o1, o2) -> {
				double k = o1.distance - o2.distance;
				if(k > 0){
					return 1;
				}else if(k == 0){
					return 0;
				}else{
					return -1;
				}
			});
			
			this.recommend = distance.get(0).shop;
		}
	}
	
	class DistanceCmp{
		public ShopInfo shop;
		public double distance;
		
		public DistanceCmp(ShopInfo shop, double distance) {
			super();
			this.shop = shop;
			this.distance = distance;
		}
	}
	
	static class CalcDistance{
		private static final double EARTH_RADIUS = 6378.137;
	    
	    private static double rad(double d) {
	        return d * Math.PI / 180.0;
	    }
	    
	    private static double getDistance(double lgtd1, double lttd1, double lgtd2, double lttd2) {
	        double radLat1 = rad(lttd1);
	        double radLat2 = rad(lttd2);
	        double a = radLat1 - radLat2;
	        double b = rad(lgtd1) - rad(lgtd2);
	        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
	                + Math.cos(radLat1) * Math.cos(radLat2)
	                * Math.pow(Math.sin(b / 2), 2)));
	        s = s * EARTH_RADIUS;
	        s = Math.round(s * 10000d) / 10000d;
	        s = s*1000;
	        
	        return s;  
	    }
	}
}
