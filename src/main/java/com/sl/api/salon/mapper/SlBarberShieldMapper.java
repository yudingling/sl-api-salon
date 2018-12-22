package com.sl.api.salon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.api.salon.model.db.SlBarberShield;
import com.zeasn.common.mybatis.MyMapper;

public interface SlBarberShieldMapper extends MyMapper<SlBarberShield> {
	
	@Select("select a.bbt_id, a.bbt_stm, a.bbt_etm from sl_barber_shield a where a.u_id = #{uId} and a.bbt_etm > #{currentTs}")
	List<SlBarberShield> getShieldTime(@Param("uId") String uId, @Param("currentTs") Long currentTs);

}
