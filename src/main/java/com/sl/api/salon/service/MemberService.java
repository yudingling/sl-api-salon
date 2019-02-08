package com.sl.api.salon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.sl.api.salon.mapper.SlMsgMapper;
import com.sl.api.salon.mapper.SlUserLevelMapper;
import com.sl.api.salon.mapper.SlUserMapper;
import com.sl.api.salon.mapper.SlUserVoucherMapper;
import com.sl.api.salon.model.MemberInfo;
import com.sl.api.salon.model.UserLevelInfo;
import com.sl.api.salon.model.UserVoucherInfo;
import com.sl.api.salon.model.UserVoucherInfoFromDB;
import com.sl.common.model.SToken;
import com.sl.common.model.db.SlMsg;
import com.sl.common.model.db.SlUser;

@Service
public class MemberService {
	@Autowired
	private SlUserMapper slUserMapper;
	@Autowired
	private SlUserVoucherMapper slUserVoucherMapper;
	@Autowired
	private SlUserLevelMapper slUserLevelMapper;
	@Autowired
	private SlMsgMapper msgMapper;
	@Autowired
	private CommonService commonService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private MsgService msgService;
	
	public MemberInfo getInfo(SToken token){
		long ts = System.currentTimeMillis();
		
		SlUser user = this.slUserMapper.selectByPrimaryKey(token.getUserId());
		
		List<UserLevelInfo> levels = this.slUserLevelMapper.getUserLevel(token.getUserId(), ts);
		
		List<UserVoucherInfoFromDB> vouchers = this.slUserVoucherMapper.getUserVouchers(token.getUserId(), ts);
		
		return new MemberInfo(
				user.getuId(), 
				user.getuNm(), 
				this.commonService.getIconUrl(user), 
				user.getuPhone(),
				CollectionUtils.isNotEmpty(levels) ? levels.get(0) : null, 
				this.getVouchers(vouchers),
				this.reservationService.getCurrentReservation(token),
				this.orderService.getCurrentOrder(token),
				this.msgService.getMsgs(token, false, 0, 2),
				this.getUnReadMsgCount(token));
	}
	
	private Integer getUnReadMsgCount(SToken token){
		Example example = new Example(SlMsg.class);
	    example.createCriteria().andEqualTo("uId", token.getUserId()).andEqualTo("msgReaded", 0);
	    
	    return this.msgMapper.selectCountByExample(example);
	}
	
	private List<UserVoucherInfo> getVouchers(List<UserVoucherInfoFromDB> vouchers){
		Map<Long, UserVoucherInfo> result = new HashMap<>();
		
		if(CollectionUtils.isNotEmpty(vouchers)){
			for(UserVoucherInfoFromDB item : vouchers){
				UserVoucherInfo info = result.get(item.getUvId());
				if(info == null){
					info = new UserVoucherInfo(item);
					
					result.put(item.getUvId(), info);
				}
				
				info.addProject(item);
			}
		}
		
		return new ArrayList<>(result.values());
	}
}
