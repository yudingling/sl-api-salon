package com.sl.api.salon;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;
import com.zeasn.common.bootstrap.MySpringBootApplication;
import com.zeasn.common.cors.EnableCors;
import com.zeasn.common.feign.MyEnableFeignClients;

@MySpringBootApplication(scanBasePackages = {"com.sl.api.salon", "com.zeasn.common.component.global"})
@EnableEurekaClient
@MyEnableFeignClients
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = {"com.sl.**.mapper"})
@EnableCors
public class SlApiSalonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlApiSalonApplication.class, args);
	}
}
