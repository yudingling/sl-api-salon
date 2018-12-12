package com.sl.api.salon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import com.zeasn.common.feign.api.SnowFlakeApi;

@FeignClient(value = "snowflake")
public interface DefaultSnowflakeApi extends SnowFlakeApi {

}
