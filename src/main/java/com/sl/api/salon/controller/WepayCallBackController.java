package com.sl.api.salon.controller;

import java.util.SortedMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sl.api.salon.service.WePayCallBackService;
import com.sl.api.salon.util.WePayUtil;
import com.zeasn.common.log.MyLog;

@RestController
@RequestMapping("/wepay/callback")
public class WepayCallBackController {
	private static final MyLog log = MyLog.getLog(WepayCallBackController.class);
	
	@Autowired
	private WePayCallBackService wePayCallBackService;
	
	@RequestMapping(method = RequestMethod.POST)
	public String post(HttpServletRequest request, HttpServletResponse response){
		try {
			SortedMap<String, String> packageParams = WePayUtil.getCallBackSignParams(request);
			
			if(MapUtils.isEmpty(packageParams)){
				throw new Exception("微信支付回调通知失败！");
			}
			
			return this.wePayCallBackService.callback(packageParams);
			
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
			return null;
		}
	}
}
