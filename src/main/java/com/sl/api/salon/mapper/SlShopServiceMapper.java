package com.sl.api.salon.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.sl.common.model.db.SlShopService;
import com.zeasn.common.mybatis.MyMapper;

public interface SlShopServiceMapper extends MyMapper<SlShopService> {
	
	@Select("select count(a.sps_id) from sl_shop_service a where a.shop_id = #{shopId} and a.sps_available=1 and a.sps_etm >= #{curTs}")
	@ResultType(Integer.class)
	int getServicedShop(@Param("shopId") Long shopId, @Param("curTs") Long curTs);
}
