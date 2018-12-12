package com.sl.api.salon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import com.zeasn.common.feign.api.AutoReportApi;

@FeignClient(value = "autoreport")
public interface DefaultAutoReportApi extends AutoReportApi {
}
