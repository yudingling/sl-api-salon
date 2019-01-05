package com.sl.api.salon.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sl.api.salon.model.SToken;
import com.sl.api.salon.service.TokenService;
import com.sl.api.salon.util.Constant;
import com.zeasn.common.model.result.ApiError;
import com.zeasn.common.model.result.ApiResult;

public class TokenFilter extends BaseFilter {
	private TokenService tokenService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        this.tokenService  = ctx.getBean(TokenService.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		FilterHttpServletRequest newRequest = new FilterHttpServletRequest(req);
		
		String tokenStr = newRequest.getParameter(Constant.REQUEST_PARAM_TOKEN);
		if(StringUtils.hasText(tokenStr)){
			SToken token = this.tokenService.getToken(tokenStr);
			if(token != null){
				newRequest.setToken(token);
				
				chain.doFilter(newRequest, response);
				return;
			}
		}
		
		this.error(ApiResult.error(ApiError.TOKEN_ERROR, "token is invalid, please re-acquire"), req, resp);
	}

	@Override
	public void destroy() {
		//nothing
	}
}
