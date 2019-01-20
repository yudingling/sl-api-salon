package com.sl.api.salon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.sl.api.salon.mapper.SlBarberProjectMapper;
import com.sl.api.salon.mapper.SlBarberShieldMapper;
import com.sl.api.salon.mapper.SlOrderMapper;
import com.sl.api.salon.mapper.SlProjectMapper;
import com.sl.api.salon.mapper.SlProjectProductMapper;
import com.sl.api.salon.mapper.SlReservationMapper;
import com.sl.api.salon.mapper.SlUserMapper;
import com.sl.api.salon.model.BarberInfo;
import com.sl.api.salon.model.BarberProject;
import com.sl.api.salon.model.BarberReservation;
import com.sl.common.model.SToken;
import com.sl.common.model.db.SlBarberProject;
import com.sl.common.model.db.SlBarberShield;
import com.sl.common.model.db.SlOrder;
import com.sl.common.model.db.SlProject;
import com.sl.common.model.db.SlProjectProduct;
import com.sl.common.model.db.SlReservation;
import com.sl.common.model.db.SlUser;

@Service
public class BarberService {
	@Autowired
	private SlUserMapper slUserMapper;
	@Autowired
	private SlBarberProjectMapper slBarberProjectMapper;
	@Autowired
	private SlProjectMapper slProjectMapper;
	@Autowired
	private SlProjectProductMapper slProjectProductMapper;
	@Autowired
	private SlReservationMapper slReservationMapper;
	@Autowired
	private SlBarberShieldMapper slBarberShieldMapper;
	@Autowired
	private SlOrderMapper slOrderMapper;
	@Autowired
	private CommonService commonService;
	
	
	public BarberReservation getBarbers(SToken token, Long shopId){
		List<SlUser> bbs = this.slUserMapper.getBarbers(token.getBrandId(), shopId);
		
		if(CollectionUtils.isEmpty(bbs)){
			return new BarberReservation();
		}
		
		Set<Long> projectIds = new HashSet<>();
		Set<Long> uIds = bbs.stream().map(SlUser::getuId).collect(Collectors.toSet());
		
		Map<Long, Map<Long, Long>> shieldMap = this.getShieldInfo(uIds);
		Map<Long, List<SlBarberProject>> bbpMap = this.getBarberProjects(uIds, projectIds);
		
		Map<Long, SlProject> projectMap;
		Map<Long, Set<Long>> pjpMap;
		
		if(CollectionUtils.isNotEmpty(projectIds)){
			projectMap = this.getProjects(projectIds);
			pjpMap = this.getProjectProducts(projectIds);
			
		}else{
			projectMap = new HashMap<>();
			pjpMap = new HashMap<>();
		}
		
		List<BarberInfo> bbInfos = new ArrayList<>();
		for(SlUser barber : bbs){
			BarberInfo tmp = this.createBarberInfo(barber, bbpMap.get(barber.getuId()), projectMap, pjpMap, shieldMap);
			if(tmp != null){
				bbInfos.add(tmp);
			}
		}
		
		return new BarberReservation(bbInfos);
	}
	
	private BarberInfo createBarberInfo(SlUser barber, List<SlBarberProject> bbProjects, Map<Long, SlProject> projectMap, 
			Map<Long, Set<Long>> pjpMap, Map<Long, Map<Long, Long>> shieldMap){
		BarberInfo br = new BarberInfo(barber, this.commonService.getIconUrl(barber), shieldMap.get(barber.getuId()));
		
		Map<Long, BarberProject> projects = new HashMap<>();
		
		if(CollectionUtils.isNotEmpty(bbProjects)){
			for(SlBarberProject tmp : bbProjects){
				SlProject pj = projectMap.get(tmp.getPjId());
				if(pj != null){
					BarberProject bp = new BarberProject(pj, tmp, pjpMap.get(tmp.getPjId()));
					projects.put(bp.getPjId(), bp);
				}
			}
		}
		
		if(projects.isEmpty()){
			return null;
			
		}else{
			br.setProjects(projects);
			return br;
		}
	}
	
	public Map<Long, Map<Long, Long>> getShieldInfo(Set<Long> uIds){
		Map<Long, List<SlReservation>> reservMap = this.getRunningReservations(uIds);
		Map<Long, List<SlOrder>> orderMap = this.getRunningOrders(uIds);
		Map<Long, List<SlBarberShield>> shieldMap = this.getBarberShield(uIds);
		
		Map<Long, Map<Long, Long>> bbShield = new HashMap<>();
		for(Long uid : uIds){
			bbShield.put(uid, this.combineShield(reservMap.get(uid), orderMap.get(uid), shieldMap.get(uid)));
		}
		
		return bbShield;
	}
	
	private Map<Long, Long> combineShield(List<SlReservation> reservs, List<SlOrder> orders, List<SlBarberShield> shields){
		Map<Long, Long> map = new HashMap<>();
		
		if(CollectionUtils.isNotEmpty(reservs)){
			for(SlReservation tmp : reservs){
				this.coverWithTimeRange(map, tmp.getRvStm(), tmp.getRvEtm());
			}
		}
		
		if(CollectionUtils.isNotEmpty(orders)){
			for(SlOrder tmp : orders){
				this.coverWithTimeRange(map, tmp.getOdStm(), tmp.getOdEtm());
			}
		}
		
		if(CollectionUtils.isNotEmpty(shields)){
			for(SlBarberShield tmp : shields){
				this.coverWithTimeRange(map, tmp.getBbtStm(), tmp.getBbtEtm());
			}
		}
		
		return map;
	}
	
	private void coverWithTimeRange(Map<Long, Long> timeMap, Long stm, Long etm){
		if(timeMap.isEmpty()){
			timeMap.put(stm, etm);
			
		}else{
			Long prevEtm = timeMap.putIfAbsent(stm, etm);
			if(prevEtm != null && prevEtm < etm){
				timeMap.put(stm, etm);
			}
		}
	}
	
	private Map<Long, List<SlReservation>> getRunningReservations(Set<Long> uIds){
		Example example = new Example(SlReservation.class);
	    example.createCriteria().andIn("rvBarberUid", uIds).andEqualTo("rvAvailable", 1).andEqualTo("rvActive", 0).andGreaterThan("rvEtm", System.currentTimeMillis());
	    example.selectProperties("rvId", "rvBarberUid", "rvStm", "rvEtm");
	    
	    List<SlReservation> reservs = this.slReservationMapper.selectByExample(example);
	    
	    Map<Long, List<SlReservation>> map = new HashMap<>();
	    
	    if(CollectionUtils.isNotEmpty(reservs)){
	    	for(SlReservation rv : reservs){
				List<SlReservation> tmp = map.get(rv.getRvBarberUid());
				if(tmp == null){
					tmp = new ArrayList<>();
					map.put(rv.getRvBarberUid(), tmp);
				}
				
				tmp.add(rv);
			}
	    }
	    
	    return map;
	}
	
	private Map<Long, List<SlOrder>> getRunningOrders(Set<Long> uIds){
		Example example = new Example(SlOrder.class);
	    example.createCriteria().andIn("odBarberUid", uIds).andEqualTo("odPaied", 0).andGreaterThan("odEtm", System.currentTimeMillis());
	    example.selectProperties("odId", "odBarberUid", "odStm", "odEtm");
	    
	    List<SlOrder> orders = this.slOrderMapper.selectByExample(example);
	    
	    Map<Long, List<SlOrder>> map = new HashMap<>();
	    
	    if(CollectionUtils.isNotEmpty(orders)){
	    	for(SlOrder od : orders){
				List<SlOrder> tmp = map.get(od.getOdBarberUid());
				if(tmp == null){
					tmp = new ArrayList<>();
					map.put(od.getOdBarberUid(), tmp);
				}
				
				tmp.add(od);
			}
	    }
	    
	    return map;
	}
	
	private Map<Long, List<SlBarberShield>> getBarberShield(Set<Long> uIds){
		Example example = new Example(SlBarberShield.class);
	    example.createCriteria().andIn("uId", uIds).andGreaterThan("bbtEtm", System.currentTimeMillis());
	    example.selectProperties("bbtId", "uId", "bbtStm", "bbtEtm");
	    
	    List<SlBarberShield> shields = this.slBarberShieldMapper.selectByExample(example);
	    
	    Map<Long, List<SlBarberShield>> map = new HashMap<>();
	    
	    if(CollectionUtils.isNotEmpty(shields)){
	    	for(SlBarberShield sb : shields){
				List<SlBarberShield> tmp = map.get(sb.getuId());
				if(tmp == null){
					tmp = new ArrayList<>();
					map.put(sb.getuId(), tmp);
				}
				
				tmp.add(sb);
			}
	    }
	    
	    return map;
	}
	
	private Map<Long, List<SlBarberProject>> getBarberProjects(Set<Long> uIds, Set<Long> projectIds){
		Example example = new Example(SlBarberProject.class);
	    example.createCriteria().andIn("uId", uIds);
	    
	    List<SlBarberProject> bbProjects = this.slBarberProjectMapper.selectByExample(example);
	    
	    Map<Long, List<SlBarberProject>> bbpMap = new HashMap<>();
	    
	    if(CollectionUtils.isNotEmpty(bbProjects)){
			for(SlBarberProject bbp : bbProjects){
				List<SlBarberProject> tmp = bbpMap.get(bbp.getuId());
				if(tmp == null){
					tmp = new ArrayList<>();
					bbpMap.put(bbp.getuId(), tmp);
				}
				
				tmp.add(bbp);
				projectIds.add(bbp.getPjId());
			}
		}
	    
	    return bbpMap;
	}
	
	private Map<Long, SlProject> getProjects(Set<Long> pjIds){
		Example example = new Example(SlProject.class);
	    example.createCriteria().andIn("pjId", pjIds).andEqualTo("pjEnable", 1);
	    
	    List<SlProject> projects = this.slProjectMapper.selectByExample(example);
	    
	    Map<Long, SlProject> projectMap = new HashMap<>();
	    
	    if(CollectionUtils.isNotEmpty(projects)){
			for(SlProject pj : projects){
				projectMap.put(pj.getPjId(), pj);
			}
		}
	    
	    return projectMap;
	}
	
	private Map<Long, Set<Long>> getProjectProducts(Set<Long> pjIds){
		Example example = new Example(SlProjectProduct.class);
	    example.createCriteria().andIn("pjId", pjIds);
	    
	    List<SlProjectProduct> ppList = this.slProjectProductMapper.selectByExample(example);
	    
	    Map<Long, Set<Long>> pjpMap = new HashMap<>();
	    
	    if(CollectionUtils.isNotEmpty(ppList)){
	    	for(SlProjectProduct pp : ppList){
				Set<Long> tmp = pjpMap.get(pp.getPjId());
				if(tmp == null){
					tmp = new HashSet<>();
					pjpMap.put(pp.getPjId(), tmp);
				}
				
				tmp.add(pp.getPdId());
			}
	    }
		
	    return pjpMap;
	}
	
}
