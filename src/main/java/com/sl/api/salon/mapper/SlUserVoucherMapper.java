package com.sl.api.salon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.api.salon.model.UserVoucherInfoFromDB;
import com.sl.common.model.db.SlUserVoucher;
import com.zeasn.common.mybatis.MyMapper;

public interface SlUserVoucherMapper extends MyMapper<SlUserVoucher> {
	
	@Select("select a.uv_id, a.uv_amount, a.uv_stm, a.uv_etm, c.pj_id, c.pj_nm from sl_user_voucher a inner join sl_user_voucher_project b on a.uv_id = b.uv_id"
			+ " inner join sl_project c on b.pj_id = c.pj_id where a.u_id = #{uId}"
			+ " and a.uv_available=1 and a.uv_etm >= #{curTs} order by a.crt_ts desc")
	List<UserVoucherInfoFromDB> getUserVouchers(@Param("uId") Long uId, @Param("curTs") Long curTs);
	
	@Select("select a.* from sl_user_voucher a inner join sl_user_voucher_project b on a.uv_id = b.uv_id"
			+ " where a.u_id = #{uId} and a.uv_available=1 and a.uv_etm >= #{curTs}"
			+ " and b.pj_id in (#{pjId}, 0) ")
	List<SlUserVoucher> getAvailVouchers(@Param("uId") Long uId, @Param("pjId") Long pjId, @Param("curTs") Long curTs);
}
