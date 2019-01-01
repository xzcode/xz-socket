package com.xzcode.socket.core.message.invoker;

import com.xzcode.socket.core.message.SocketOnMessage;

/**
 * 请求消息调用模型
 * 
 * @author zai
 * 2019-01-01 22:11:15
 */
public class OnMessagerInvoker implements IMessageInvoker{
	
	
	/**
	 * 请求标识
	 */
	private String requestTag;
	
	/**
	 * 接收消息的class类型
	 */
	private Class<?> requestMessageClass;
	
	
	/**
	 * 消息调用对象
	 */
	private SocketOnMessage<Object> onMessage;


	@Override
	public Object invoke(String requestTag, Object message) throws Exception {
		onMessage.onMessage(message);
		return null;
	}


	public String getRequestTag() {
		return requestTag;
	}


	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}


	public Class<?> getRequestMessageClass() {
		return requestMessageClass;
	}


	public void setRequestMessageClass(Class<?> requestMessageClass) {
		this.requestMessageClass = requestMessageClass;
	}


	public SocketOnMessage<Object> getOnMessage() {
		return onMessage;
	}


	public void setOnMessage(SocketOnMessage<Object> onMessage) {
		this.onMessage = onMessage;
	}


	@Override
	public String getSendTag() {
		return null;
	}
	
	
	

}
