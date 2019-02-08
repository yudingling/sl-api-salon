package com.sl.api.salon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.sl.api.salon.mapper.SlBarberProjectMapper;
import com.sl.api.salon.mapper.SlOrderEvaluationMapper;
import com.sl.api.salon.mapper.SlOrderMapper;
import com.sl.api.salon.mapper.SlOrderProductMapper;
import com.sl.api.salon.mapper.SlProductMapper;
import com.sl.api.salon.mapper.SlProjectMapper;
import com.sl.api.salon.mapper.SlReservationMapper;
import com.sl.api.salon.mapper.SlReservationProductMapper;
import com.sl.api.salon.mapper.SlUserLevelMapper;
import com.sl.api.salon.mapper.SlUserMapper;
import com.sl.api.salon.mapper.SlUserVoucherMapper;
import com.sl.api.salon.model.BarberInfo;
import com.sl.api.salon.model.BarberProject;
import com.sl.api.salon.model.HistoryOrder;
import com.sl.api.salon.model.OrderInfo;
import com.sl.api.salon.model.ProductInfo;
import com.sl.api.salon.model.UserLevelInfo;
import com.sl.api.salon.util.FloatUtil;
import com.sl.common.model.OrderConfirmStatus;
import com.sl.common.model.SToken;
import com.sl.common.model.db.SlBarberProject;
import com.sl.common.model.db.SlOrder;
import com.sl.common.model.db.SlOrderEvaluation;
import com.sl.common.model.db.SlOrderProduct;
import com.sl.common.model.db.SlProduct;
import com.sl.common.model.db.SlProject;
import com.sl.common.model.db.SlReservation;
import com.sl.common.model.db.SlUser;
import com.sl.common.model.db.SlUserVoucher;
import com.sl.common.model.mq.OrderConfirmedMsg;
import com.zeasn.common.feign.api.SnowFlakeApi;
import com.zeasn.common.model.IdGetter;

@Service
public class OrderService {
	@Autowired
	private SlReservationMapper slReservationMapper;
	@Autowired
	private SlProductMapper slProductMapper;
	@Autowired
	private SlReservationProductMapper slReservationProductMapper;
	@Autowired
	private SlProjectMapper slProjectMapper;
	@Autowired
	private SlBarberProjectMapper slBarberProjectMapper;
	@Autowired
	private SlUserLevelMapper slUserLevelMapper;
	@Autowired
	private SlOrderMapper slOrderMapper;
	@Autowired
	private SlOrderProductMapper slOrderProductMapper;
	@Autowired
	private SlOrderEvaluationMapper slOrderEvaluationMapper;
	@Autowired
	private SlUserMapper slUserMapper;
	@Autowired
	private SlUserVoucherMapper userVoucherMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	@Autowired
	private CommonService commonService;
	
	@Autowired
    private RabbitTemplate template;
	@Resource
	private FanoutExchange websocketExchange;
	
	public List<HistoryOrder> getHistoryOrders(SToken token, Integer startIndex, Integer size){
		List<HistoryOrder> orders = this.slOrderMapper.getHistoryOrders(token.getUserId(), startIndex, size);
		
		if(CollectionUtils.isNotEmpty(orders)){
			this.setEvaValue(orders);
			this.setBarberInfo(orders);
		}
		
		return orders;
	}
	
	private void setBarberInfo(List<HistoryOrder> orders){
		Set<Long> uIds = orders.stream().map(HistoryOrder::getOdBarberUid).collect(Collectors.toSet());
		
		Example example = new Example(SlUser.class);
	    example.createCriteria().andIn("uId", uIds);
		
		List<SlUser> barbers = this.slUserMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(barbers)){
			Map<Long, SlUser> bbMap = new HashMap<>();
			barbers.forEach(bb -> {
				bbMap.put(bb.getuId(), bb);
			});
	    	
	    	for(HistoryOrder od : orders){
	    		SlUser tmp = bbMap.get(od.getOdBarberUid());
	    		if(tmp != null){
	    			od.setOdBarberUnm(tmp.getuNm());
	    			od.setOdBarberIcon(this.commonService.getIconUrl(tmp));
	    		}
	    	}
		}
	}
	
	private void setEvaValue(List<HistoryOrder> orders){
		Set<Long> odIds = orders.stream().map(HistoryOrder::getOdId).collect(Collectors.toSet());
		
		Example example = new Example(SlOrderEvaluation.class);
	    example.createCriteria().andIn("odId", odIds);
	    
	    List<SlOrderEvaluation> data = this.slOrderEvaluationMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(data)){
	    	Map<Long, SlOrderEvaluation> evaMap = new HashMap<>();
	    	
	    	for(SlOrderEvaluation item : data){
	    		SlOrderEvaluation tmp = evaMap.get(item.getOdId());
	    		if(tmp == null || tmp.getCrtTs() < item.getCrtTs()){
	    			evaMap.put(item.getOdId(), item);
	    		}
	    	}
	    	
	    	for(HistoryOrder od : orders){
	    		SlOrderEvaluation tmp = evaMap.get(od.getOdId());
	    		if(tmp != null){
	    			od.setOdEva(tmp.getOdevaVal());
	    			od.setOdEvaDesc(tmp.getOdevaDesc());
	    		}
	    	}
	    }
	}
	
	public OrderInfo getHistoryOrder(SToken token, Long odId){
		Example example = new Example(SlOrder.class);
	    example.createCriteria().andEqualTo("odId", odId).andEqualTo("odUid", token.getUserId());
	    
	    List<SlOrder> data = this.slOrderMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(data)){
	    	SlOrder order = data.get(0);
	    	
	    	List<SlProduct> products = this.slOrderProductMapper.getProductFromOrder(order.getOdId());
	    	
	    	SlUser barber = this.slUserMapper.selectByPrimaryKey(order.getOdBarberUid());
	    	if(barber == null){
	    		return null;
	    	}
	    	
	    	SlProject project = this.slProjectMapper.selectByPrimaryKey(order.getPjId());
	    	if(project == null){
	    		return null;
	    	}
	    	
	    	Example evaExample = new Example(SlOrderEvaluation.class);
		    example.createCriteria().andEqualTo("odId", odId);
		    List<SlOrderEvaluation> evaList = this.slOrderEvaluationMapper.selectByExample(evaExample);
	    	
	    	return this.getInfo(
	    			order, 
	    			barber,
	    			project,
	    			products,
	    			evaList);
	    	
	    }else{
	    	return null;
	    }
	}
	
	@Transactional(rollbackFor = Exception.class)
	public OrderInfo createOrder(SToken token, Long rvId){
		SlReservation reservation = this.checkReservation(token, rvId);
		if(reservation == null){
			return null;
		}
		
		List<SlProduct> products = this.slReservationProductMapper.getProductFromReservation(rvId);
		
		SlUser barber = this.slUserMapper.selectByPrimaryKey(reservation.getRvBarberUid());
		if(barber == null){
    		return null;
    	}
		
		SlProject project = this.slProjectMapper.selectByPrimaryKey(reservation.getPjId());
		if(project == null){
    		return null;
    	}
		
		SlBarberProject bbProject = this.getSlBarberProject(reservation.getRvBarberUid(), reservation.getPjId());
		Double pjPrice = bbProject != null ? bbProject.getBbpPrice() : project.getPjPrice();
		
		SlOrder order = this.newOrder(reservation, pjPrice, products);
		
		return order != null ? this.getInfo(order, barber, project, products, null) : null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean activeOrder(Long odId){
		SlOrder order = this.slOrderMapper.selectByPrimaryKey(odId);
		if(order != null && order.getOdConfirm() == OrderConfirmStatus.UNCONFIRM.getValue()){
			Double payPrice = order.getOdPayPrice();
			
			Double odVoucherPrice = 0.0;
			List<SlUserVoucher> usedVouchers = new ArrayList<>();
			
			List<SlUserVoucher> vouchers = this.userVoucherMapper.getAvailVouchers(order.getOdUid(), order.getPjId(), System.currentTimeMillis());
			if(CollectionUtils.isNotEmpty(vouchers)){
				for(SlUserVoucher vo : vouchers){
					odVoucherPrice += vo.getUvAmount();
					usedVouchers.add(vo);
					
					if(odVoucherPrice >= payPrice){
						break;
					}
				}
			}
			
			odVoucherPrice = FloatUtil.toFix(odVoucherPrice, 2);
			
			if(odVoucherPrice > payPrice){
				odVoucherPrice = payPrice;
			}
			payPrice -= odVoucherPrice;
			
			
			SlOrder upt = new SlOrder();
			upt.setOdConfirm(OrderConfirmStatus.CONFIRMED.getValue());
			upt.setOdPayPrice(payPrice);
			upt.setOdVoucherPrice(odVoucherPrice);
			upt.setUptTs(System.currentTimeMillis());
			
			Example example = new Example(SlOrder.class);
		    example.createCriteria().andEqualTo("odId", odId).andEqualTo("odConfirm", OrderConfirmStatus.UNCONFIRM.getValue());
		    
			if(this.slOrderMapper.updateByExampleSelective(upt, example) == 1){
				if(CollectionUtils.isNotEmpty(usedVouchers)){
					this.updateVoucherUsed(usedVouchers);
				}
				
				OrderConfirmedMsg msg = new OrderConfirmedMsg(order.getOdUid(), order.getOdId());
				
				this.template.convertAndSend(this.websocketExchange.getName(), "", msg);
				
				return true;
			}
		}
		
		return false;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean cancelOrder(SToken token, Long odId){
		Example example = new Example(SlOrder.class);
	    example.createCriteria().andEqualTo("odId", odId).andEqualTo("odUid", token.getUserId()).andEqualTo("odConfirm", OrderConfirmStatus.UNCONFIRM.getValue());
		
		int len = this.slOrderMapper.selectCountByExample(example);
		if(len == 1){
			SlOrder upt = new SlOrder();
			upt.setOdConfirm(OrderConfirmStatus.CANCELED.getValue());
			upt.setUptTs(System.currentTimeMillis());
		    
			return this.slOrderMapper.updateByExampleSelective(upt, example) == 1;
			
		}else{
			return false;
		}
	}
	
	public OrderInfo getCurrentOrder(SToken token){
		Example example = new Example(SlOrder.class);
	    example.createCriteria().andEqualTo("odUid", token.getUserId()).andEqualTo("odPaied", 0).andNotEqualTo("odConfirm", OrderConfirmStatus.CANCELED.getValue());
	    
	    List<SlOrder> data = this.slOrderMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(data)){
	    	return this.getOrderInfo(data.get(0));
	    	
	    }else{
	    	return null;
	    }
	}
	
	public OrderInfo getOrderInfo(Long odId){
		SlOrder order = this.slOrderMapper.selectByPrimaryKey(odId);
		
		return order != null ? this.getOrderInfo(order) : null;
	}
	
	private OrderInfo getOrderInfo(SlOrder order){
		List<SlProduct> products = this.slOrderProductMapper.getProductFromOrder(order.getOdId());
    	
    	SlUser barber = this.slUserMapper.selectByPrimaryKey(order.getOdBarberUid());
    	if(barber == null){
    		return null;
    	}
    	
    	SlProject project = this.slProjectMapper.selectByPrimaryKey(order.getPjId());
    	if(project == null){
    		return null;
    	}
    	
    	return this.getInfo(
    			order, 
    			barber,
    			project,
    			products,
    			null);
	}
	
	private OrderInfo getInfo(SlOrder order, SlUser barber, SlProject project, List<SlProduct> products, List<SlOrderEvaluation> evaList){
		OrderInfo info = new OrderInfo(order);
		
		BarberInfo bbInfo = new BarberInfo(barber, this.commonService.getIconUrl(barber), null);
		
		SlBarberProject bbp = this.getSlBarberProject(barber.getuId(), project.getPjId());
		BarberProject barberProject = bbp != null ? new BarberProject(project, bbp) : new BarberProject(project);
		
		List<ProductInfo> pdInfos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(products)){
			for(SlProduct pd : products){
				pdInfos.add(new ProductInfo(pd, this.commonService.getIconUrl(pd)));
			}
		}
		
		info.setBarber(bbInfo);
		info.setProject(barberProject);
		info.setProducts(pdInfos);
		
		info.setEvaList(evaList);
		
		return info;
	}
	
	private SlOrder newOrder(SlReservation reservation, Double pjPrice, List<SlProduct> products){
		Long ts = System.currentTimeMillis();
		
		Long stm = ts;
		Long etm = ts + (reservation.getRvEtm() - reservation.getRvStm());
		
		int idSize = 1;
		idSize += CollectionUtils.isNotEmpty(products) ? products.size() : 0;
		IdGetter<Long> idGetter = IdGetter.initLong(idSize, this.snowFlakeApi);
		
		Double pdPrice = 0.0;
		if(CollectionUtils.isNotEmpty(products)){
			for(SlProduct pd : products){
				pdPrice += pd.getPdPrice();
			}
		}
		
		pdPrice = FloatUtil.toFix(pdPrice, 2);
		pjPrice = FloatUtil.toFix(pjPrice, 2);
		Double totalPrice = pjPrice + pdPrice;
		Double disCount = this.getUserDiscount(reservation.getRvUid(), ts);
		Double payPrice = FloatUtil.toFix(pjPrice * disCount, 2) + pdPrice;
		
		//create a order which waiting for confirm
		SlOrder order = new SlOrder(
				idGetter.next(), 
				reservation.getRvId(), 
				reservation.getShopId(), 
				reservation.getRvUid(), 
				reservation.getRvBarberUid(), 
				reservation.getPjId(), 
				stm,
				etm, 
				pjPrice,
				pdPrice, 
				totalPrice, 
				disCount, 
				0.0, 
				0.0, 
				payPrice, 
				0, 
				null, 
				null, 
				0, 
				OrderConfirmStatus.UNCONFIRM.getValue(),
				ts,
				ts);
		
		if(this.slOrderMapper.insert(order) == 1){
			if(CollectionUtils.isNotEmpty(products)){
				List<SlOrderProduct> ops = new ArrayList<>();
				
				for(SlProduct pd : products){
					ops.add(new SlOrderProduct(
							idGetter.next(), 
							order.getOdId(), 
							pd.getPdtpId(), 
							pd.getPdId(), 
							pd.getPdPrice(), 
							ts, 
							ts));
				}
				
				this.slOrderProductMapper.insertList(ops);
			}
			
			this.activeReservation(reservation.getRvId());
			
			return order;
			
		}else{
			return null;
		}
	}
	
	private void activeReservation(Long rvId){
		SlReservation sr = new SlReservation();
		sr.setRvId(rvId);
		sr.setUptTs(System.currentTimeMillis());
		sr.setRvActive(1);
		
		this.slReservationMapper.updateByPrimaryKeySelective(sr);
	}
	
	private void updateVoucherUsed(List<SlUserVoucher> usedVouchers){
		Example example = new Example(SlUserVoucher.class);
	    example.createCriteria().andIn("uvId", usedVouchers.stream().map(SlUserVoucher::getUvId).collect(Collectors.toSet()));
	    
	    SlUserVoucher upt = new SlUserVoucher();
	    upt.setUptTs(System.currentTimeMillis());
	    upt.setUvAvailable(0);
		
		this.userVoucherMapper.updateByExampleSelective(upt, example);
	}
	
	private Double getUserDiscount(Long uId, Long ts){
		List<UserLevelInfo> levels = this.slUserLevelMapper.getUserLevel(uId, ts);
		
		return CollectionUtils.isNotEmpty(levels) ? levels.get(0).getUlDiscount() : 1.0;
	}
	
	private SlReservation checkReservation(SToken token, Long rvId){
		Example example = new Example(SlReservation.class);
	    example.createCriteria().andEqualTo("rvId", rvId).andEqualTo("rvUid", token.getUserId()).andEqualTo("rvAvailable", 1).andEqualTo("rvActive", 0);
	    
	    List<SlReservation> data = this.slReservationMapper.selectByExample(example);
	    return CollectionUtils.isNotEmpty(data) ? data.get(0) : null;
	}
	
	private SlBarberProject getSlBarberProject(Long barberId, Long projectId){
		Example example = new Example(SlBarberProject.class);
	    example.createCriteria().andEqualTo("uId", barberId).andEqualTo("pjId", projectId);
	    
	    List<SlBarberProject> bbProjects = this.slBarberProjectMapper.selectByExample(example);
	    return CollectionUtils.isNotEmpty(bbProjects) ? bbProjects.get(0) : null;
	}
}
