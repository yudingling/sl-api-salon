package com.sl.api.salon.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.apache.commons.collections.MapUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSON;
import com.sl.api.salon.model.OrderInfo;
import com.sl.api.salon.model.SApiError;
import com.sl.api.salon.service.OrderService;
import com.sl.api.salon.service.ReservationService;
import com.sl.common.model.OrderConfirmStatus;
import com.sl.common.model.SToken;
import com.sl.common.model.mq.OrderConfirmedMsg;
import com.sl.common.model.mq.WebSocketMsg;
import com.sl.common.util.Constant;
import com.zeasn.common.log.MyLog;
import com.zeasn.common.log.RuntimeLog;
import com.zeasn.common.model.result.ApiError;
import com.zeasn.common.model.result.ApiObjectResult;
import com.zeasn.common.model.result.ApiResult;

public class CreateOrderHandler extends TextWebSocketHandler {
	private static final MyLog log = MyLog.getLog(CreateOrderHandler.class);
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ReservationService reservationService;
	
	private Map<Long, Map<String, WebSocketSession>> webSocketMap = new ConcurrentHashMap<>();
	
	/**
	 * use default container factory defined in @MqAutoConfiguration which concurrency is set to cpu nums.
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(
				name = "#{websocketQueue.name}", 
				autoDelete = "#{websocketQueue.autoDelete}", 
				durable = "#{websocketQueue.durable}", 
				exclusive = "#{websocketQueue.exclusive}"), 
			exchange = @Exchange(
					name = "#{websocketExchange.name}", 
					type = "#{websocketExchange.type}",
					autoDelete = "#{websocketExchange.autoDelete}",
					delayed = "#{websocketExchange.delayed}",
					durable = "#{websocketExchange.durable}",
					internal = "#{websocketExchange.internal}")))
    public void receive(WebSocketMsg msg){
		if(msg instanceof OrderConfirmedMsg){
			this.handleMsg((OrderConfirmedMsg) msg);
		}
	}
	
	private void handleMsg(OrderConfirmedMsg msg){
		Map<String, WebSocketSession> map = this.webSocketMap.get(msg.getUid());
		if(MapUtils.isNotEmpty(map)){
			map.values().forEach(session -> this.sendMsg(session, msg));
		}
	}
	
	private void sendMsg(WebSocketSession session, OrderConfirmedMsg msg){
		try {
			if(OrderConfirmStatus.CONFIRMED.equals(msg.getConfirmed())){
				OrderInfo orderInfo = this.orderService.getOrderInfo(msg.getOdId());
				msg.setData(orderInfo);
			}
			
			ApiResult result = new ApiObjectResult<>(msg);
			session.sendMessage(new TextMessage(JSON.toJSONString(new ResponseData(1, result))));
		} catch (IOException e) {
			log.error(RuntimeLog.build(msg, "websocket sending failed: " + e.getMessage()), e);
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		SToken token = (SToken) session.getAttributes().get(Constant.REQUEST_PARAM_TOKEN);
		
		if(token != null){
			Map<String, WebSocketSession> map = this.webSocketMap.computeIfAbsent(token.getUserId(), uId -> new ConcurrentHashMap<>());
			map.put(session.getId(), session);
		}
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		this.removeMap(session);
		
		if(session.isOpen()) {
			session.close();
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		this.removeMap(session);
	}
	
	private void removeMap(WebSocketSession session){
		SToken token = (SToken) session.getAttributes().get(Constant.REQUEST_PARAM_TOKEN);
		
		if(token != null){
			Map<String, WebSocketSession> map = this.webSocketMap.get(token.getUserId());
			if(map != null){
				map.remove(session.getId());
			}
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		SToken token = (SToken) session.getAttributes().get(Constant.REQUEST_PARAM_TOKEN);
		
		if(token != null){
			ApiResult result = null;
			String payload = message.getPayload();
			
			if(StringUtils.isNotEmpty(payload)){
				RequestData data = JSON.parseObject(payload, RequestData.class);
				
				result = this.createOrder(token, data.rvId);
				
			}else{
				result = ApiResult.error(ApiError.ARGUMENT_ERROR, "create order failed due to error arguments");
			}
			
			session.sendMessage(new TextMessage(JSON.toJSONString(new ResponseData(0, result))));
		}
	}
	
	private ApiResult createOrder(SToken token, Long rvId){
		if(this.reservationService.hasUnPaiedOrder(token)){
			return ApiResult.error(SApiError.ORDER_UNPAIED, "got unpaied order, please finish the unpaied order before create a new order!");
		}
		
		OrderInfo order = this.orderService.createOrder(token, rvId);
		if(order == null){
			return ApiResult.error(ApiError.ARGUMENT_ERROR, "create order failed due to error arguments");	
		}
		
		return new ApiObjectResult<>(order);
	}
	
	static class RequestData{
		private Long rvId;
		
		public Long getRvId() {
			return rvId;
		}

		public void setRvId(Long rvId) {
			this.rvId = rvId;
		}

		public RequestData(){
			super();
		}

		public RequestData(Long rvId) {
			super();
			this.rvId = rvId;
		}
	}
	
	static class ResponseData{
		/**
		 * 0: order created,  1: order confirm
		 */
		private Integer resultType;
		private ApiResult result;
		
		public Integer getResultType() {
			return resultType;
		}
		public void setResultType(Integer resultType) {
			this.resultType = resultType;
		}
		public ApiResult getResult() {
			return result;
		}
		public void setResult(ApiResult result) {
			this.result = result;
		}
		
		public ResponseData(){
			super();
		}
		
		public ResponseData(Integer resultType, ApiResult result) {
			super();
			this.resultType = resultType;
			this.result = result;
		}
	}
}
