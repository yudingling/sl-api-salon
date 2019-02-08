package com.sl.api.salon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.api.salon.model.HistoryOrder;
import com.sl.common.model.db.SlOrder;
import com.zeasn.common.mybatis.MyMapper;

public interface SlOrderMapper extends MyMapper<SlOrder> {
	
	@Select("select a.od_id, a.shop_id, b.shop_nm, a.od_uid, a.od_barber_uid, a.od_stm, a.pj_id, c.pj_nm, a.od_total_price, a.od_confirm "
			+ "from sl_order a inner join sl_shop b on a.shop_id = b.shop_id inner join sl_project c on a.pj_id = c.pj_id "
			+ "inner join sl_user u on a.od_barber_uid = u.u_id "
			+ "where a.od_uid = #{uId} order by a.od_stm desc limit #{startIndex}, #{size}")
	List<HistoryOrder> getHistoryOrders(@Param("uId") Long uId, @Param("startIndex") Integer startIndex, @Param("size") Integer size);
}
