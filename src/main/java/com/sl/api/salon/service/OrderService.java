package com.sl.api.salon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
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
import com.sl.api.salon.model.BarberInfo;
import com.sl.api.salon.model.BarberProject;
import com.sl.api.salon.model.HistoryOrder;
import com.sl.api.salon.model.OrderInfo;
import com.sl.api.salon.model.ProductInfo;
import com.sl.api.salon.model.UserLevelInfo;
import com.sl.common.model.SToken;
import com.sl.common.model.db.SlBarberProject;
import com.sl.common.model.db.SlOrder;
import com.sl.common.model.db.SlOrderEvaluation;
import com.sl.common.model.db.SlOrderProduct;
import com.sl.common.model.db.SlProduct;
import com.sl.common.model.db.SlProject;
import com.sl.common.model.db.SlReservation;
import com.sl.common.model.db.SlUser;
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
	private SnowFlakeApi snowFlakeApi;
	@Autowired
	private CommonService commonService;
	
	public List<HistoryOrder> getHistoryOrders(SToken token, Integer startIndex, Integer size){
		List<HistoryOrder> orders = this.slOrderMapper.getHistoryOrders(token.getUserId(), startIndex, size);
		
		if(CollectionUtils.isNotEmpty(orders)){
			this.setEvaValue(orders);
		}
		
		return orders;
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
	    		}
	    	}
	    }
	}
	
	public OrderInfo getHistoryOrder(SToken token, Long odId){
		Example example = new Example(SlOrder.class);
	    example.createCriteria().andEqualTo("odId", odId).andEqualTo("odUid", token.getUserId()).andEqualTo("odPaied", 1);
	    
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
	
	public OrderInfo getCurrentOrder(SToken token){
		Example example = new Example(SlOrder.class);
	    example.createCriteria().andEqualTo("odUid", token.getUserId()).andEqualTo("odPaied", 0);
	    
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
	    	
	    	return this.getInfo(
	    			order, 
	    			barber,
	    			project,
	    			products,
	    			null);
	    	
	    }else{
	    	return null;
	    }
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
		
		Double totalPrice = pjPrice + pdPrice;
		
		Double disCount = this.getUserDiscount(reservation.getRvUid(), ts);
		Double payPrice = pjPrice * disCount + pdPrice;
		
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
				0,
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
			
			return order;
			
		}else{
			return null;
		}
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
