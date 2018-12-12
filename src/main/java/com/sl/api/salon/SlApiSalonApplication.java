package com.sl.api.salon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;
import com.zeasn.common.apilog.EnableApiLog;
import com.zeasn.common.bootstrap.MySpringBootApplication;
import com.zeasn.common.cors.EnableCors;
import com.zeasn.common.feign.MyEnableFeignClients;
import com.zeasn.common.switchcall.EnableAssertSwitch;

@MySpringBootApplication(scanBasePackages = {"com.sl.api.salon", "com.zeasn.common.component.global"})
@EnableEurekaClient
@MyEnableFeignClients
@EnableTransactionManagement
@MapperScan(basePackages = {"com.sl.**.mapper"})
@ServletComponentScan
@EnableAssertSwitch
@EnableCors
@EnableCircuitBreaker
@EnableApiLog
public class SlApiSalonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlApiSalonApplication.class, args);
	}
}
