package com.sl.api.salon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.common.model.UserForToken;
import com.sl.common.model.db.SlUser;
import com.zeasn.common.mybatis.MyMapper;

public interface SlUserMapper extends MyMapper<SlUser> {
	
	@Select("select a.u_id, a.bd_id, a.role_id, a.u_avatar from sl_user a inner join sl_user_wechat b on a.u_id = b.u_id and b.uw_unionid = #{wechatUnionId}"
			+ " and a.u_active=1 and a.u_disabled=0 ")
	List<UserForToken> getUserFromWechat(@Param("wechatUnionId") String wechatUnionId);
	
	@Select("select a.* from sl_user a inner join sl_user_barber b on a.u_id = b.u_id and b.shop_id = #{shopId} and b.bd_id = #{bdId} and a.u_active=1 and a.u_disabled=0 ")
	List<SlUser> getBarbers(@Param("bdId") String bdId, @Param("shopId") Long shopId);
	
	@Select("select a.* from sl_user a inner join sl_user_barber b on a.u_id = b.u_id and b.shop_id = #{shopId} and b.u_id = #{barberId} and a.u_active=1 and a.u_disabled=0 ")
	List<SlUser> getBarber(@Param("shopId") Long shopId, @Param("barberId") Long barberId);
}
