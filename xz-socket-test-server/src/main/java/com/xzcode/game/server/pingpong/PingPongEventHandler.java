package com.xzcode.game.server.pingpong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xzcode.game.server.constant.SocketSessionAttrKeys;
import com.xzcode.game.server.tag.SocketTags;
import com.xzcode.socket.core.annotation.SocketComponent;
import com.xzcode.socket.core.annotation.SocketOnEvent;
import com.xzcode.socket.core.annotation.SocketRequest;
import com.xzcode.socket.core.event.SocketEvents;
import com.xzcode.socket.core.utils.SocketServerService;
@Component
@SocketComponent
public class PingPongEventHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PingPongEventHandler.class);
	
	
	@Autowired
	private SocketServerService sss;
	
	/**
	 * 读写空闲触发心跳失败累计次数
	 * 
	 * 
	 * @author zai
	 * 2017-09-19
	 */
	@SocketOnEvent(SocketEvents.IdleState.ALL_IDLE)
	public void heatbeatCalc() {
		
		PingPongInfo pingPongInfo = (PingPongInfo) sss.getSession().getAttribute(SocketSessionAttrKeys.HEART_BEAT_MODEL_KEY);
		
		if (pingPongInfo == null) {
			pingPongInfo = new PingPongInfo();
			sss.getSession().addAttribute(SocketSessionAttrKeys.HEART_BEAT_MODEL_KEY, pingPongInfo);
		}
		
		if (pingPongInfo.isHeartBeatLost()) {
			sss.disconnect();
		}
		
		//SocketServerUtil.send(SocketTags.HeartBeat.PING);
		
		//增加心跳失败次数
		pingPongInfo.heartBeatLostTimesIncrease();
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\n-----ALL_IDLE------   : {}", pingPongInfo);
		}
		
	}
	
	/**
	 * 接收并处理客户端ping包, 发送pong包
	 * 
	 * 
	 * @author zai
	 * 2017-09-19
	 */
	@SocketRequest(SocketTags.HeartBeat.PING)
	public void ping() {
		
		PingPongInfo pingPongInfo = (PingPongInfo) sss.getSession().getAttribute(SocketSessionAttrKeys.HEART_BEAT_MODEL_KEY);
		
		if (pingPongInfo == null) {
			pingPongInfo = new PingPongInfo();
			sss.getSession().addAttribute(SocketSessionAttrKeys.HEART_BEAT_MODEL_KEY, pingPongInfo);
		}
		
		sss.send(SocketTags.HeartBeat.PONG, () -> {
			System.out.println("mission success");
		});
		
		//重置心跳失败累计次数
		pingPongInfo.heartBeatLostTimesReset();
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received Ping-----> HeartBeatInfo:{}", pingPongInfo);
		}
	}
	
}
