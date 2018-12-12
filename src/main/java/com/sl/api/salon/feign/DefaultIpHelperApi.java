package com.sl.api.salon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import com.zeasn.common.feign.api.IpHelperApi;

@FeignClient(value = "iphelper")
public interface DefaultIpHelperApi extends IpHelperApi {
}