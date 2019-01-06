package com.sl.api.salon.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sl.api.salon.feign.FileApi;
import com.sl.api.salon.model.BarberWorkExt;
import com.sl.common.model.db.SlBrand;
import com.sl.common.model.db.SlProduct;
import com.sl.common.model.db.SlShopEvent;
import com.sl.common.model.db.SlShopImage;
import com.sl.common.model.db.SlUser;
import com.zeasn.common.log.MyLog;

@Service
public class CommonService {
	private static final MyLog log = MyLog.getLog(CommonService.class);
	
	@Value("${salon.file}")
	private String fileUrl;
	@Autowired
	private FileApi fileApi;
	
	public String getIconUrl(SlUser user){
		if(StringUtils.isNotEmpty(user.getuAvatar())){
			return user.getuAvatar();
			
		}else{
			Long fileId = user.getuIcon() != null ? user.getuIcon() : 0l;
			
			return this.getIconUrl(fileId);
		}
	}
	
	public String getIconUrl(SlProduct product){
		return product.getPdIcon() != null ? this.getIconUrl(product.getPdIcon()) : null;
	}
	
	public String getIconUrl(SlBrand brand){
		return brand.getBdLogo() != null ? this.getIconUrl(brand.getBdLogo()) : null;
	}
	
	public String getIconUrl(SlShopImage image){
		return image.getSpiImg() != null ? this.getIconUrl(image.getSpiImg()) : null;
	}
	
	public String getIconUrl(SlShopEvent event){
		return event.getEventImg() != null ? this.getIconUrl(event.getEventImg()) : null;
	}
	
	public String getIconUrl(BarberWorkExt work){
		return work.getBbwImg() != null ? this.getIconUrl(work.getBbwImg()) : null;
	}
	
	/**
	 * get image url. never throw exception
	 */
	private String getIconUrl(Long fileId){
		try{
			return String.format("%s/%s", this.fileUrl, this.fileApi.getIcon(fileId));
			
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
			return null;
		}
	}
}
