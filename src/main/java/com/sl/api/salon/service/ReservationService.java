package com.sl.api.salon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.google.common.collect.Sets;
import com.sl.api.salon.mapper.SlBarberProjectMapper;
import com.sl.api.salon.mapper.SlOrderMapper;
import com.sl.api.salon.mapper.SlProductMapper;
import com.sl.api.salon.mapper.SlProjectMapper;
import com.sl.api.salon.mapper.SlReservationMapper;
import com.sl.api.salon.mapper.SlReservationProductMapper;
import com.sl.api.salon.mapper.SlShopMapper;
import com.sl.api.salon.mapper.SlShopServiceMapper;
import com.sl.api.salon.mapper.SlUserMapper;
import com.sl.api.salon.model.BarberInfo;
import com.sl.api.salon.model.BarberProject;
import com.sl.api.salon.model.ProductInfo;
import com.sl.api.salon.model.ReservationInfo;
import com.sl.api.salon.model.exception.BarberTimeShiledException;
import com.sl.common.model.OrderConfirmStatus;
import com.sl.common.model.SToken;
import com.sl.common.model.db.SlBarberProject;
import com.sl.common.model.db.SlOrder;
import com.sl.common.model.db.SlProduct;
import com.sl.common.model.db.SlProject;
import com.sl.common.model.db.SlReservation;
import com.sl.common.model.db.SlReservationProduct;
import com.sl.common.model.db.SlShop;
import com.sl.common.model.db.SlUser;
import com.sl.common.util.Constant;
import com.zeasn.common.feign.api.SnowFlakeApi;
import com.zeasn.common.model.IdGetter;
import com.zeasn.common.redis.DistributedLock;

@Service
public class ReservationService {
	@Autowired
	private SlOrderMapper slOrderMapper;
	@Autowired
	private SlShopMapper slShopMapper;
	@Autowired
	private SlProjectMapper slProjectMapper;
	@Autowired
	private SlUserMapper slUserMapper;
	@Autowired
	private SlProductMapper slProductMapper;
	@Autowired
	private SlReservationMapper slReservationMapper;
	@Autowired
	private SlReservationProductMapper slReservationProductMapper;
	@Autowired
	private SlBarberProjectMapper slBarberProjectMapper;
	@Autowired
	private SlShopServiceMapper shopServiceMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	@Autowired
	private BarberService barberService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private DistributedLock distributedLock;
	
	public boolean isShopServiced(Long shopId){
		//still available in 5 days.
		Long endTs = System.currentTimeMillis() + 432000000;
		
		return this.shopServiceMapper.getServicedShop(shopId, endTs) > 0;
	}
	
	public boolean hasReservation(SToken token){
		Example example = new Example(SlReservation.class);
	    example.createCriteria().andEqualTo("rvUid", token.getUserId()).andEqualTo("rvAvailable", 1).andEqualTo("rvActive", 0);
	    
	    return this.slReservationMapper.selectCountByExample(example) > 0;
	}
	
	public ReservationInfo getCurrentReservation(SToken token){
		Example example = new Example(SlReservation.class);
	    example.createCriteria().andEqualTo("rvUid", token.getUserId()).andEqualTo("rvAvailable", 1).andEqualTo("rvActive", 0);
	    
	    List<SlReservation> data = this.slReservationMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(data)){
	    	SlReservation reservation = data.get(0);
	    	
	    	List<SlProduct> products = this.slReservationProductMapper.getProductFromReservation(reservation.getRvId());
	    	
	    	SlUser barber = this.slUserMapper.selectByPrimaryKey(reservation.getRvBarberUid());
	    	if(barber == null){
	    		return null;
	    	}
	    	
	    	SlProject project = this.slProjectMapper.selectByPrimaryKey(reservation.getPjId());
	    	if(project == null){
	    		return null;
	    	}
	    	
	    	return this.getInfo(
	    			reservation, 
	    			barber,
	    			project,
	    			products);
	    	
	    }else{
	    	return null;
	    }
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteReservation(SToken token, Long rvId){
		SlReservation upt = new SlReservation();
		upt.setUptTs(System.currentTimeMillis());
		upt.setRvAvailable(0);
		
		Example example = new Example(SlReservation.class);
	    example.createCriteria().andEqualTo("rvId", rvId).andEqualTo("rvUid", token.getUserId()).andEqualTo("rvActive", 0);
		
		return this.slReservationMapper.updateByExampleSelective(upt, example) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean updateReservationProduct(SToken token, Long rvId, Long oldPdId, Long newPdId){
		Example example = new Example(SlReservationProduct.class);
	    example.createCriteria().andEqualTo("rvId", rvId).andEqualTo("rvUid", token.getUserId()).andEqualTo("pdId", oldPdId);
	    
	    SlProduct newPd = this.slProductMapper.selectByPrimaryKey(newPdId);
    	
    	if(newPd != null && this.slReservationProductMapper.deleteByExample(example) == 1){
    		Long ts = System.currentTimeMillis();
    		
    		SlReservationProduct sp = new SlReservationProduct(
		    		this.snowFlakeApi.nextId(), rvId, newPd.getPdtpId(), newPd.getPdId(), token.getUserId(), ts, ts);
    		
    		return this.slReservationProductMapper.insert(sp) == 1;
    	}
	    
	    return false;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public ReservationInfo createReservation(SToken token, Long shopId, Long pjId, Long barberId, Long stm, Set<Long> pdIds) throws BarberTimeShiledException{
		if(!this.checkShopId(token, shopId)){
			return null;
		}
		
		SlUser barber = this.checkBarberId(shopId, barberId);
		if(barber == null){
			return null;
		}
		
		SlProject project = this.checkPjId(token, pjId);
		if(project == null){
			return null;
		}
		
		List<SlProduct> products = null;
		if(CollectionUtils.isNotEmpty(pdIds)){
			products = this.checkPdIds(token, pdIds);
			if(products == null || products.size() != pdIds.size()){
				return null;
			}
		}
		
		SlReservation reservation = this.doReserve(token, shopId, project, barber, stm, products);
		return reservation != null ? this.getInfo(reservation, barber, project, products) : null;
	}
	
	private SlReservation doReserve(SToken token, Long shopId, SlProject project, SlUser barber, Long stm, List<SlProduct> products) throws BarberTimeShiledException{
		String key = String.format("%s%d", Constant.LOCK_PREFIX_RESERVATION, barber.getuId());
		RLock lock = this.distributedLock.getLock(key);
		try{
			lock.lock();
			
			Map<Long, Map<Long, Long>> shiledTs = this.barberService.getShieldInfo(Sets.newHashSet(barber.getuId()));
			if(MapUtils.isNotEmpty(shiledTs)){
				Map<Long, Long> shiled = shiledTs.get(barber.getuId());
				boolean timeOk = true;
				
				if(MapUtils.isNotEmpty(shiled)){
					for(Entry<Long, Long> entry : shiled.entrySet()){
						if(entry.getKey() <= stm && entry.getValue() > stm){
							timeOk = false;
							break;
						}
					}
				}
				
				if(timeOk){
					return this.newReservation(token, shopId, project, barber.getuId(), stm, products);
					
				}else{
					throw new BarberTimeShiledException(barber.getuId(), shiled);
				}
			}
			
		}finally{
			lock.unlock();
		}
		
		return null;
	}
	
	private ReservationInfo getInfo(SlReservation reservation, SlUser barber, SlProject project, List<SlProduct> products){
		ReservationInfo info = new ReservationInfo(reservation);
		
		BarberInfo bbInfo = new BarberInfo(barber, this.commonService.getIconUrl(barber));
		
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
		
		return info;
	}
	
	public boolean hasUnPaiedOrder(SToken token){
		Example example = new Example(SlOrder.class);
	    example.createCriteria().andEqualTo("odUid", token.getUserId()).andEqualTo("odPaied", 0).andNotEqualTo("odConfirm", OrderConfirmStatus.CANCELED.getValue());
	    
	    return this.slOrderMapper.selectCountByExample(example) > 0;
	}
	
	private SlReservation newReservation(SToken token, Long shopId, SlProject project, Long barberId, Long stm, List<SlProduct> products){
		Long ts = System.currentTimeMillis();
		
		Long etm = stm + (long)(project.getPjHour() * 3600000);
		
		int idSize = 1;
		idSize += CollectionUtils.isNotEmpty(products) ? products.size() : 0;
		IdGetter<Long> idGetter = IdGetter.initLong(idSize, this.snowFlakeApi);
		
		SlReservation reserve = new SlReservation(
				idGetter.next(), 
				shopId, 
				token.getUserId(), 
				barberId, 
				project.getPjId(), 
				stm, 
				etm, 
				1, 
				0, 
				0, 
				ts, 
				ts);
		
		if(this.slReservationMapper.insert(reserve) == 1){
			if(CollectionUtils.isNotEmpty(products)){
				List<SlReservationProduct> rps = new ArrayList<>();
				
				for(SlProduct pd : products){
					rps.add(new SlReservationProduct(idGetter.next(), reserve.getRvId(), pd.getPdtpId(), pd.getPdId(), token.getUserId(), ts, ts));
				}
				
				this.slReservationProductMapper.insertList(rps);
			}
			
			return reserve;
			
		}else{
			return null;
		}
	}
	
	private boolean checkShopId(SToken token, Long shopId){
		Example example = new Example(SlShop.class);
	    example.createCriteria().andEqualTo("shopId", shopId).andEqualTo("bdId", token.getBrandId()).andEqualTo("shopEnable", 1);
	    
	    return this.slShopMapper.selectCountByExample(example) > 0;
	}
	
	private SlProject checkPjId(SToken token, Long pjId){
		Example example = new Example(SlProject.class);
	    example.createCriteria().andEqualTo("pjId", pjId).andEqualTo("bdId", token.getBrandId()).andEqualTo("pjEnable", 1);
	    
	    List<SlProject> data = this.slProjectMapper.selectByExample(example);
	    return CollectionUtils.isNotEmpty(data) ? data.get(0) : null;
	}
	
	private SlUser checkBarberId(Long shopId, Long barberId){
		List<SlUser> barbers = this.slUserMapper.getBarber(shopId, barberId);
	    
	    return CollectionUtils.isNotEmpty(barbers) ? barbers.get(0) : null;
	}
	
	private List<SlProduct> checkPdIds(SToken token, Set<Long> pdIds){
		Example example = new Example(SlProduct.class);
	    example.createCriteria().andIn("pdId", pdIds).andEqualTo("bdId", token.getBrandId()).andEqualTo("pdEnable", 1);
	    
	    return this.slProductMapper.selectByExample(example);
	}
	
	private SlBarberProject getSlBarberProject(Long barberId, Long projectId){
		Example example = new Example(SlBarberProject.class);
	    example.createCriteria().andEqualTo("uId", barberId).andEqualTo("pjId", projectId);
	    
	    List<SlBarberProject> bbProjects = this.slBarberProjectMapper.selectByExample(example);
	    return CollectionUtils.isNotEmpty(bbProjects) ? bbProjects.get(0) : null;
	}
}
