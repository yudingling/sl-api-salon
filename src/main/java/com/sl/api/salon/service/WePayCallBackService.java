package com.sl.api.salon.service;

import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sl.api.salon.mapper.SlOrderMapper;
import com.sl.api.salon.mapper.SlShopServiceMapper;
import com.sl.api.salon.mapper.SlShopServiceOrderMapper;
import com.sl.api.salon.mapper.SlUserLevelMapper;
import com.sl.api.salon.mapper.SlUserLevelOrderMapper;
import com.sl.api.salon.mapper.SlWechatPayMapper;
import com.sl.api.salon.model.PayTradeType;
import com.sl.api.salon.model.WePayResult;
import com.sl.api.salon.util.WePayUtil;
import com.sl.common.model.PayType;
import com.sl.common.model.db.SlOrder;
import com.sl.common.model.db.SlShopService;
import com.sl.common.model.db.SlShopServiceOrder;
import com.sl.common.model.db.SlUserLevel;
import com.sl.common.model.db.SlUserLevelOrder;
import com.sl.common.model.db.SlWechatPay;
import com.zeasn.common.daemon.Daemon;
import com.zeasn.common.daemon.IWriteBack;
import com.zeasn.common.feign.api.SnowFlakeApi;

@Service
public class WePayCallBackService implements IWriteBack<WePayResult> {
	@Value("${salon.wepayApiKey}")
	private String wepayApiKey;
	
	@Autowired
	private Daemon daemon;
	@Autowired
	private SlWechatPayMapper wechatPayMapper;
	@Autowired
	private SlOrderMapper orderMapper;
	@Autowired
	private SlUserLevelMapper userLevelMapper;
	@Autowired
	private SlUserLevelOrderMapper userLevelOrderMapper;
	@Autowired
	private SlShopServiceMapper shopServiceMapper;
	@Autowired
	private SlShopServiceOrderMapper shopServiceOrderMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	
	public String callback(SortedMap<String, String> packageParams){
		if (WePayUtil.isTenpaySign(packageParams, this.wepayApiKey)) {
			// first to check return_code
			if("SUCCESS".equals(packageParams.get("return_code"))){
				//business data																
				String tradeNo = packageParams.get("out_trade_no");
				boolean isSuccess = "SUCCESS".equals(packageParams.get("result_code"));
				
				this.daemon.protect(this, new WePayResult(tradeNo, isSuccess));
				
				return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
			}
		}
		
		return "";
	}

	@Override
	public boolean writeBack(WePayResult data) {
		SlWechatPay pay = this.wechatPayMapper.selectByPrimaryKey(Long.parseLong(data.getTradeNo()));
		if(pay != null){
			boolean isSuccess = data.getSuccess();
			
			this.updateTradeStatus(pay, isSuccess);
			
			switch(PayTradeType.valueOf(pay.getWpayFkTp())){
				case ORDER:
					this.updateOrderStatus(pay, isSuccess);
					break;
					
				case LEVEL_UP:
					this.updateUserLevelOrderStatus(pay, isSuccess);
					break;
					
				case SHOP_FEE:
					this.updateShopFeeOrderStatus(pay, isSuccess);
					break;
					
				default:
					break;
			}
		}
		
		return true;
	}
	
	private void updateTradeStatus(SlWechatPay pay, boolean isSuccess){
		SlWechatPay upt = new SlWechatPay();
		upt.setWpayId(pay.getWpayId());
		upt.setUptTs(System.currentTimeMillis());
		upt.setWpaySuccess(isSuccess ? 1 : 2);
		
		this.wechatPayMapper.updateByPrimaryKeySelective(upt);
	}
	
	private void updateOrderStatus(SlWechatPay pay, boolean isSuccess){
		if(isSuccess){
			SlOrder order = new SlOrder();
			order.setOdId(pay.getWpayFkId());
			order.setUptTs(System.currentTimeMillis());
			order.setOdPaied(1);
			order.setOdPaiedTp(PayType.WECHAT.toString());
			order.setOdPaiedTs(System.currentTimeMillis());
			
			this.orderMapper.updateByPrimaryKeySelective(order);
		}
	}
	
	private void updateUserLevelOrderStatus(SlWechatPay pay, boolean isSuccess){
		if(isSuccess){
			SlUserLevelOrder order = new SlUserLevelOrder();
			order.setUlodId(pay.getWpayFkId());
			order.setUptTs(System.currentTimeMillis());
			order.setUlodPaiedTp(PayType.WECHAT.toString());
			order.setUlodPaiedTs(System.currentTimeMillis());
			
			this.userLevelOrderMapper.updateByPrimaryKeySelective(order);
			
			SlUserLevelOrder updated = this.userLevelOrderMapper.selectByPrimaryKey(order.getUlodId());
			if(updated != null){
				SlUserLevel ul = new SlUserLevel(this.snowFlakeApi.nextId(), updated);
				
				this.userLevelMapper.insert(ul);
			}
		}
	}
	
	private void updateShopFeeOrderStatus(SlWechatPay pay, boolean isSuccess){
		if(isSuccess){
			SlShopServiceOrder order = new SlShopServiceOrder();
			order.setSpsodId(pay.getWpayFkId());
			order.setUptTs(System.currentTimeMillis());
			order.setSpsodPaiedTp(PayType.WECHAT.toString());
			order.setSpsodPaiedTs(System.currentTimeMillis());
			
			this.shopServiceOrderMapper.updateByPrimaryKeySelective(order);
			
			SlShopServiceOrder updated = this.shopServiceOrderMapper.selectByPrimaryKey(order.getSpsodId());
			if(updated != null){
				SlShopService ul = new SlShopService(this.snowFlakeApi.nextId(), updated);
				
				this.shopServiceMapper.insert(ul);
			}
		}
	}
}
