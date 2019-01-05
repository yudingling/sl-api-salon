package com.sl.api.salon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sl.api.salon.model.BarberWorkExt;
import com.sl.api.salon.model.db.SlBarberWork;
import com.zeasn.common.mybatis.MyMapper;

public interface SlBarberWorkMapper extends MyMapper<SlBarberWork> {
	
	@Select("select a.bbw_id, a.u_id, a.bbw_title, a.bbw_img, a.bbw_like, c.bbwl_id "
			+ " from sl_barber_work a inner join sl_user_barber b on a.u_id = b.u_id and a.bd_id = b.bd_id and b.shop_id = #{shopId} and b.bd_id = #{bdId}"
			+ " left join sl_barber_work_like c on a.bbw_id = c.bbw_id"
			+ " order by a.crt_ts desc limit #{startIndex}, #{size}")
	List<BarberWorkExt> getWorksByTime(@Param("bdId") String bdId, @Param("shopId") Long shopId, @Param("startIndex") Integer startIndex, @Param("size") Integer size);
	
	@Select("select a.bbw_id, a.u_id, a.bbw_title, a.bbw_img, a.bbw_like, c.bbwl_id "
			+ " from sl_barber_work a inner join sl_user_barber b on a.u_id = b.u_id and a.bd_id = b.bd_id and b.shop_id = #{shopId} and b.bd_id = #{bdId}"
			+ " left join sl_barber_work_like c on a.bbw_id = c.bbw_id"
			+ " order by a.bbw_like desc limit #{startIndex}, #{size}")
	List<BarberWorkExt> getWorksByLike(@Param("bdId") String bdId, @Param("shopId") Long shopId, @Param("startIndex") Integer startIndex, @Param("size") Integer size);
	
	@Update("update sl_barber_work set bbw_like = bbw_like + 1 where bbw_id = #{workId}")
	void likeWork(@Param("workId") Long workId);
	
	@Update("update sl_barber_work set bbw_like = bbw_like - 1 where bbw_id = #{workId}")
	void dislikeWork(@Param("workId") Long workId);
}
