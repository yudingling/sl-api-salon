package com.sl.api.salon.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.zeasn.common.feign.api.GeneralDataApi;

@FeignClient(value = "general-data")
public interface DefaultGeneralDataApi extends GeneralDataApi {
}
