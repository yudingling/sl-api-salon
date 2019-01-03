package com.sl.api.salon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.api.salon.model.db.SlMsg;
import com.zeasn.common.mybatis.MyMapper;

public interface SlMsgMapper extends MyMapper<SlMsg> {
	
	@Select("select a.* from sl_msg a where a.u_id = #{uId} and a.msg_readed = #{msgReaded} order by a.crt_ts desc limit #{startIndex}, #{size}")
	List<SlMsg> getMsgs(@Param("uId") Long uId, @Param("msgReaded") Integer msgReaded, @Param("startIndex") Integer startIndex, @Param("size") Integer size);
}
