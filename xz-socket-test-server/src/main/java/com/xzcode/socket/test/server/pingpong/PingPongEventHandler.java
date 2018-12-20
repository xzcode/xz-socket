package com.xzcode.socket.test.server.pingpong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.annotation.SocketComponent;
import com.xzcode.socket.core.annotation.SocketOnEvent;
import com.xzcode.socket.core.annotation.SocketRequest;
import com.xzcode.socket.core.event.SocketEvents;
import com.xzcode.socket.core.utils.SocketServerUtil;
import com.xzcode.socket.test.server.constant.SocketSessionAttrKeys;
import com.xzcode.socket.test.server.tag.SocketTags;

@SocketComponent
public class PingPongEventHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PingPongEventHandler.class);
	
	/**
	 * 读写空闲触发，发送ping包
	 * 
	 * 
	 * @author zai
	 * 2017-09-19
	 */
	@SocketOnEvent(SocketEvents.IdleState.ALL_IDLE)
	public void ping() {
		
		PingPongInfo pingPongInfo = (PingPongInfo) SocketServerUtil.getSession().getAttribute(SocketSessionAttrKeys.HEART_BEAT_MODEL_KEY);
		
		if (pingPongInfo == null) {
			pingPongInfo = new PingPongInfo();
			SocketServerUtil.getSession().addAttribute(SocketSessionAttrKeys.HEART_BEAT_MODEL_KEY, pingPongInfo);
		}
		
		if (pingPongInfo.isHeartBeatLost()) {
			SocketServerUtil.disconnect();
		}
		
		SocketServerUtil.send(SocketTags.HeartBeat.PING);
		
		pingPongInfo.heartBeatLostTimesIncrease();
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Sending Ping-----> HeartBeatInfo:{}", pingPongInfo);
		}
		
	}
	
	/**
	 * 接收并处理客户端pong包
	 * 
	 * 
	 * @author zai
	 * 2017-09-19
	 */
	@SocketRequest(SocketTags.HeartBeat.PONG)
	public void pong() {
		
		PingPongInfo pingPongInfo = (PingPongInfo) SocketServerUtil.getSession().getAttribute(SocketSessionAttrKeys.HEART_BEAT_MODEL_KEY);
		
		if (pingPongInfo == null) {
			pingPongInfo = new PingPongInfo();
			SocketServerUtil.getSession().addAttribute(SocketSessionAttrKeys.HEART_BEAT_MODEL_KEY, pingPongInfo);
		}
		
		pingPongInfo.heartBeatLostTimesReset();
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received Pong-----> HeartBeatInfo:{}", pingPongInfo);
		}
	}
	
}
