package com.sl.api.salon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.api.salon.model.db.SlProduct;
import com.sl.api.salon.model.db.SlReservationProduct;
import com.zeasn.common.mybatis.MyMapper;

public interface SlReservationProductMapper extends MyMapper<SlReservationProduct> {
	
	@Select("select a.* from sl_product a inner join sl_reservation_product b on a.pd_id = b.pd_id and b.rv_id = #{rvId}")
	List<SlProduct> getProductFromReservation(@Param("rvId") Long rvId);

}