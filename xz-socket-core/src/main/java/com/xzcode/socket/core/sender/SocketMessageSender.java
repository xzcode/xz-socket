package com.xzcode.socket.core.sender;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.utils.json.SocketJsonUtil;

/**
 * 消息发送器
 * 
 * 
 * @author zai 2017-07-30 23:14:52
 */
public class SocketMessageSender {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SocketMessageSender.class);
	
	public static void send(Channel channel, String sendTag, Object message) {
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(SendModel.create(sendTag, message));
		}else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Channel is inactived! Message will not be send, sendTag:{}, message:{}", sendTag, SocketJsonUtil.gson().toJson(message));
			}
		}
	}
	
	public static void send(Channel channel, SendModel sendModel) {
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(sendModel);			
		}else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Channel is inactived! Message will not be send, SendModel:{}", SocketJsonUtil.gson().toJson(sendModel));
			}
		}
	}
}
