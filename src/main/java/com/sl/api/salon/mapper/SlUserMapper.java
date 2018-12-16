package com.sl.api.salon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.api.salon.model.db.SlUser;
import com.sl.api.salon.model.token.UserForToken;
import com.zeasn.common.mybatis.MyMapper;

public interface SlUserMapper extends MyMapper<SlUser> {
	
	@Select("select a.u_id, a.bd_id, a.role_id, a.u_avatar from sl_user a inner join sl_user_wechat b on a.u_id = b.u_id and b.uw_unionid = #{wechatUnionId}"
			+ " and a.u_active=1 and a.u_disabled=0 ")
	List<UserForToken> getUserFromWechat(@Param("wechatUnionId") String wechatUnionId);
}
