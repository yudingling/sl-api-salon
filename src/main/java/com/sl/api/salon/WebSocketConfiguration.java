package com.sl.api.salon;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.AnonymousQueue.Base64UrlNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.sl.api.salon.websocket.CreateOrderHandler;
import com.sl.api.salon.websocket.WSInterceptor;
import com.sl.common.util.Constant;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(createOrderHandler(), "/ws/createOrder").addInterceptors(wsInterceptor()).setAllowedOrigins("*");
	}
	
	@Bean
	public CreateOrderHandler createOrderHandler(){
		return new CreateOrderHandler();
	}
	
	@Bean
	public WSInterceptor wsInterceptor(){
		return new WSInterceptor();
	}
	
	/**
	 * inject by name
	 */
	@Bean
    public FanoutExchange websocketExchange() {
        return new FanoutExchange(Constant.RABBIT_FANOUT_WESOCKETMSG);
    }
	
	/**
	 * inject by name
	 */
	@Bean
    public Queue websocketQueue() {
		//exclusive should be false. (exclusive queue can not reconnect)
		return new Queue(Base64UrlNamingStrategy.DEFAULT.generateName(), false, false, true);
    }
}
