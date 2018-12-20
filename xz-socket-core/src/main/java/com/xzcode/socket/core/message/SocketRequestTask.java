package com.xzcode.socket.core.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.filter.MessageFilterMapper;
import com.xzcode.socket.core.sender.SendModel;
import com.xzcode.socket.core.sender.SocketMessageSender;
import com.xzcode.socket.core.session.SocketSessionUtil;
import com.xzcode.socket.core.session.imp.SocketSession;

/**
 * socket执行任务
 * 
 * 
 * @author zai 2017-07-30 20:17:18
 */
public class SocketRequestTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SocketRequestTask.class);
	
	
	private MessageMethodInvoker messageMethodInvoker;
	
	public void setMessageMethodInvokeMapper(MessageMethodInvoker messageMethodInvoker) {
		this.messageMethodInvoker = messageMethodInvoker;
	}
	
	/**
	 * socket消息体对象
	 */
	private Object message;
	
	/**
	 * socket消息体对象
	 */
	private SocketSession session;
	
	/**
	 * 请求标识
	 */
	private String requestTag;
	
	
	public SocketRequestTask() {
	}
	
	

	public SocketRequestTask(String requestTag, SocketSession session, Object message, MessageMethodInvoker messageMethodInvoker) {
		super();
		this.message = message;
		this.session = session;
		this.requestTag = requestTag;
		this.messageMethodInvoker = messageMethodInvoker;
	}



	@Override
	public void run() {
		
		SocketSessionUtil.setSession(this.session);
		try {
			
			
			if (!MessageFilterMapper.doFilters(requestTag, message)) {
				return;
			}
			
			Object returnObj = messageMethodInvoker.invoke(requestTag, this.message);
			if (returnObj != null) {
				SocketMessageSender.send(this.session.getChannel(), SendModel.create(messageMethodInvoker.getSendTag(requestTag), returnObj));
			}
		} catch (Exception e) {
			LOGGER.error("Socket Request Task ERROR!!", e);
		}
		SocketSessionUtil.removeSession();
		
	}
	

}
