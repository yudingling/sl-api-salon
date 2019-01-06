package com.sl.api.salon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "sl-file-salon")
public interface FileApi {
	
	@RequestMapping(value = "/inner/icon/{fileId}")
	String getIcon(@PathVariable("fileId") Long fileId);
}
