package com.sl.api.salon.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sl.api.salon.model.db.SlProduct;
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
		return String.format("%s/icon/%d", this.fileUrl, product.getPdIcon());
	}
}
