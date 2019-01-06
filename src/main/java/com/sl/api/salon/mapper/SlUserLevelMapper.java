package com.sl.api.salon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.api.salon.model.UserLevelInfo;
import com.sl.common.model.db.SlUserLevel;
import com.zeasn.common.mybatis.MyMapper;

public interface SlUserLevelMapper extends MyMapper<SlUserLevel> {
	
	@Select("select a.level_id, a.ul_discount, b.level_nm from sl_user_level a inner join sl_level b on a.level_id = b.level_id and a.u_id = #{uId}"
			+ " and a.ul_available=1 and a.ul_etm >= #{curTs} order by a.crt_ts desc")
	List<UserLevelInfo> getUserLevel(@Param("uId") Long uId, @Param("curTs") Long curTs);
}
