package com.sl.api.salon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.api.salon.model.db.SlOrderProduct;
import com.sl.api.salon.model.db.SlProduct;
import com.zeasn.common.mybatis.MyMapper;

public interface SlOrderProductMapper extends MyMapper<SlOrderProduct> {
	
	@Select("select a.* from sl_product a inner join sl_order_product b on a.pd_id = b.pd_id and b.od_id = #{odId}")
	List<SlProduct> getProductFromOrder(@Param("odId") Long odId);
}
