package com.sl.api.salon;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sl.api.salon.filter.AuthFilter;
import com.sl.api.salon.filter.TokenFilter;

@Configuration
public class WebFilterConfiguration {
	
	@Bean
	public FilterRegistrationBean<TokenFilter> filter1(){
		FilterRegistrationBean<TokenFilter> fr = new FilterRegistrationBean<>(new TokenFilter());
		fr.addUrlPatterns("/api/*", "/pages/*");
		fr.setOrder(1);
		return fr;
	}
	
	@Bean
	public FilterRegistrationBean<AuthFilter> filter2(){
		FilterRegistrationBean<AuthFilter> fr = new FilterRegistrationBean<>(new AuthFilter());
		fr.addUrlPatterns("/api/*", "/pages/*");
		fr.setOrder(2);
		return fr;
	}
}
