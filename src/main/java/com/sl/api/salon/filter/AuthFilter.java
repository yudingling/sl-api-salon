package com.sl.api.salon.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sl.api.salon.model.SApiError;
import com.sl.api.salon.service.AuthService;
import com.sl.common.filter.BaseFilter;
import com.sl.common.filter.FilterHttpServletRequest;
import com.sl.common.model.UserType;
import com.zeasn.common.model.result.ApiResult;

public class AuthFilter extends BaseFilter {
	private AuthService authService;
	private Map<UserType, Set<String>> roleApis;
	
	private static Map<String, String[]> bindMap;
	
	public static void setBindMap(Map<String, String[]> pathBindMap) {
		bindMap = pathBindMap;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        this.authService  = ctx.getBean(AuthService.class);
        
        this.roleApis = this.authService.getAllRoleApi();
	}
	
	/**
	 * wildcard check, '#' for next all， '+' for 1 level.
	 * @return the api string which match the path, if none of them was matched, return null
	 */
	private String wildcardCheck(Set<String> apiList, String path){
		for(String key: apiList){
			if(path.matches("^" + key.replace("#", "\\S*").replace("+/", "(\\S*)/")))
				return key;
		}
		
		return null;
	}
	
	private boolean matchApiPath(Set<String> apis, String path) {
		return apis.contains(path) || this.wildcardCheck(apis, path) != null;
	}
	
	private boolean authOnBindCheck(Set<String> apis, String path){
		String[] bindAuths = bindMap.get(path);
		if (bindAuths != null && bindAuths.length > 0) {
			for (String bindPath : bindAuths) {
				if(this.matchApiPath(apis, bindPath)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		FilterHttpServletRequest req = (FilterHttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		//httpMethod + ":" + url,  convert to lowerCase for compare
		String path = (req.getMethod() + ":" + req.getServletPath()).toLowerCase() 
				+ (req.getPathInfo() != null ? req.getPathInfo().toLowerCase() : "");
		
		Set<String> apis = this.roleApis.get(req.getToken().getUserType());
		
		if(CollectionUtils.isNotEmpty(apis) && (this.matchApiPath(apis, path) || this.authOnBindCheck(apis, path))){
			chain.doFilter(request, response);
			
		}else{
			this.error(ApiResult.error(SApiError.UNAUTHORIZED, "request not authorized"), req, resp);
		}
	}

	@Override
	public void destroy() {
		//nothing
	}
}
