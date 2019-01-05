package com.sl.api.salon.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sl.api.salon.model.BarberWorkExt;
import com.sl.api.salon.model.db.SlBrand;
import com.sl.api.salon.model.db.SlProduct;
import com.sl.api.salon.model.db.SlShopEvent;
import com.sl.api.salon.model.db.SlShopImage;
import com.sl.api.salon.model.db.SlUser;

@Service
public class CommonService {
	@Value("${salon.file}")
	private String fileUrl;
	
	public String getIconUrl(SlUser user){
		if(StringUtils.isNotEmpty(user.getuAvatar())){
			return user.getuAvatar();
			
		}else{
			Long fileId = user.getuIcon() != null ? user.getuIcon() : 0l;
			
			return String.format("%s/icon/%d", this.fileUrl, fileId);
		}
	}
	
	public String getIconUrl(SlProduct product){
		return product.getPdIcon() != null ? String.format("%s/icon/%d", this.fileUrl, product.getPdIcon()) : null;
	}
	
	public String getIconUrl(SlBrand brand){
		return brand.getBdLogo() != null ? String.format("%s/icon/%d", this.fileUrl, brand.getBdLogo()) : null;
	}
	
	public String getIconUrl(SlShopImage image){
		return image.getSpiImg() != null ? String.format("%s/icon/%d", this.fileUrl, image.getSpiImg()) : null;
	}
	
	public String getIconUrl(SlShopEvent event){
		return event.getEventImg() != null ? String.format("%s/icon/%d", this.fileUrl, event.getEventImg()) : null;
	}
	
	public String getIconUrl(BarberWorkExt work){
		return work.getBbwImg() != null ? String.format("%s/icon/%d", this.fileUrl, work.getBbwImg()) : null;
	}
}
