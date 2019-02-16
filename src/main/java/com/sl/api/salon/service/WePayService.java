package com.sl.api.salon.service;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.druid.util.StringUtils;
import com.sl.api.salon.mapper.SlBrandWechatMapper;
import com.sl.api.salon.mapper.SlShopMapper;
import com.sl.api.salon.mapper.SlWechatPayMapper;
import com.sl.api.salon.model.PayTradeType;
import com.sl.api.salon.model.WePayOrderInfo;
import com.sl.api.salon.util.WePayUtil;
import com.sl.common.model.SToken;
import com.sl.common.model.db.SlBrandWechat;
import com.sl.common.model.db.SlOrder;
import com.sl.common.model.db.SlShop;
import com.sl.common.model.db.SlUserLevelOrder;
import com.sl.common.model.db.SlWechatPay;
import com.zeasn.common.feign.api.SnowFlakeApi;

@Service
public class WePayService {
	@Value("${salon.api}")
	private String apiDomain;
	@Value("${salon.wepayApiKey}")
	private String wepayApiKey;
	@Value("${salon.wepayMchId}")
	private String wepayMchId;
	@Value("${salon.parentWechatAppId}")
	private String parentWechatAppId;
	
	@Autowired
	private SlBrandWechatMapper brandWechatMapper;
	@Autowired
	private SlShopMapper shopMapper;
	@Autowired
	private SlWechatPayMapper wechatPayMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	
	/**
	 * @return return null if wechat prepay order create failed.
	 */
	public WePayOrderInfo payOrder(SlOrder order, SToken token){
		SlBrandWechat bdw = this.getBrandWechat(token.getBrandId());
		if(bdw == null){
			return null;
		}
		
		String shopMchId = this.getShopMchId(order.getShopId());
		if(StringUtils.isEmpty(shopMchId)){
			return null;
		}
		
		SlWechatPay trade = this.createTrade(PayTradeType.ORDER, token.getUserId(), order.getOdId(), order.getOdPayPrice());
		if(trade == null){
			return null;
		}
		
		int feeInFen = (int)(trade.getWpayPrice() * 100);
		
		String prepayId = this.prepay(this.parentWechatAppId, this.wepayMchId, bdw.getBdwAppid(), shopMchId, token.getWeChatOpenId(), trade.getWpayId(), feeInFen, "订单支付");
		if(StringUtils.isEmpty(prepayId)){
			return null;
		}
		
		return this.signForAppRequest(bdw.getBdwAppid(), prepayId);
	}
	
	/**
	 * @return return null if wechat prepay order create failed.
	 */
	public WePayOrderInfo payOrder(SlUserLevelOrder order, SToken token){
		SlBrandWechat bdw = this.getBrandWechat(token.getBrandId());
		if(bdw == null){
			return null;
		}
		
		SlWechatPay trade = this.createTrade(PayTradeType.LEVEL_UP, token.getUserId(), order.getUlodId(), order.getUlodPrice());
		if(trade == null){
			return null;
		}
		
		int feeInFen = (int)(trade.getWpayPrice() * 100);
		
		String prepayId = this.prepay(this.parentWechatAppId, this.wepayMchId, bdw.getBdwAppid(), bdw.getBdwWechatpayId(), token.getWeChatOpenId(), trade.getWpayId(), feeInFen, "会员升级");
		if(StringUtils.isEmpty(prepayId)){
			return null;
		}
		
		return this.signForAppRequest(bdw.getBdwAppid(), prepayId);
	}
	
	private WePayOrderInfo signForAppRequest(String appId, String prepayId){
		SortedMap<String, String> packageParams = new TreeMap<>();
		packageParams.put("appId", appId);
		packageParams.put("timeStamp", (long)(System.currentTimeMillis() / 1000) + "");
		packageParams.put("nonceStr", WePayUtil.getNonceString());
		packageParams.put("package", String.format("prepay_id=%s", prepayId));
		packageParams.put("signType", "MD5");
		
		String sign = WePayUtil.createSign(packageParams, this.wepayApiKey);
		
		return new WePayOrderInfo(
				packageParams.get("package"), 
				packageParams.get("timeStamp"), 
				packageParams.get("nonceStr"), 
				packageParams.get("signType"), 
				sign);
	}
	
	private String prepay(String appId, String mchId, String subAppId, String subMchId, String subOpenId, Long tradeId, int feeInFen, String description){
		String notifyUrl =  apiDomain + "/wepay/callback";
		
		SortedMap<String, String> packageParams = new TreeMap<>();
		packageParams.put("appid", appId);
		packageParams.put("mch_id", mchId);
		packageParams.put("sub_appid", subAppId);
		packageParams.put("sub_mch_id", subMchId);
		packageParams.put("nonce_str", WePayUtil.getNonceString());
		packageParams.put("body", description);
		packageParams.put("out_trade_no", tradeId + "");
		packageParams.put("total_fee", feeInFen + "");
		packageParams.put("spbill_create_ip", "192.168.1.1");
		packageParams.put("notify_url", notifyUrl);
		packageParams.put("trade_type", "JSAPI");
		packageParams.put("sub_openid", subOpenId);
		
		String sign = WePayUtil.createSign(packageParams, this.wepayApiKey);
		packageParams.put("sign", sign);
		
		String requestXML = WePayUtil.getRequestXml(packageParams);
		String resXml = WePayUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);
		if(resXml == null){
			return null;
		}
		
		Map<String, String> map = WePayUtil.doXMLParse(resXml);
		String returnCode = map.get("return_code");
		
		if ("SUCCESS".equals(returnCode)) {
			String resultCode = map.get("result_code");
			if ("SUCCESS".equals(resultCode)) {
				return map.get("prepay_id");
			}
		}
		
		return null;
	}
	
	private SlBrandWechat getBrandWechat(String bdId){
		Example example = new Example(SlBrandWechat.class);
	    example.createCriteria().andEqualTo("bdId", bdId);
	    
	    List<SlBrandWechat> list = this.brandWechatMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(list)){
	    	return list.get(0);
	    	
	    }else{
	    	return null;
	    }
	}
	
	private String getShopMchId(Long shopId){
		SlShop shop = this.shopMapper.selectByPrimaryKey(shopId);
		
		return shop != null ? shop.getShopWechatpayId() : null;
	}
	
	private SlWechatPay createTrade(PayTradeType payType, Long uId, Long fkId, Double price){
		Long ts = System.currentTimeMillis();
		
		SlWechatPay pay = new SlWechatPay(snowFlakeApi.nextId(), uId, payType.getValue(), fkId, price, 0, ts, ts);
		if(this.wechatPayMapper.insert(pay) == 1){
			return pay;
		}else{
			return null;
		}
	}
}
