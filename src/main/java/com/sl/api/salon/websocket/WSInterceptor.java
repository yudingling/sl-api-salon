package com.sl.api.salon.websocket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.sl.api.salon.service.TokenService;
import com.sl.common.model.SToken;
import com.sl.common.util.Constant;

public class WSInterceptor implements HandshakeInterceptor {
	@Autowired
	private TokenService tokenService;

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		ServletServerHttpRequest req = (ServletServerHttpRequest) request;
		String tokenStr = req.getServletRequest().getParameter(Constant.REQUEST_PARAM_TOKEN);
		if(StringUtils.hasText(tokenStr)){
			SToken token = this.tokenService.getToken(tokenStr);
			if(token != null){
				attributes.put(Constant.REQUEST_PARAM_TOKEN, token);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
		
	}

}
