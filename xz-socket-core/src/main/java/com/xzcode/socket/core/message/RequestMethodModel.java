package com.xzcode.socket.core.message;

import java.lang.reflect.Method;

public class RequestMethodModel {
	
	/**
	 * 请求标识
	 */
	private String requestTag;
	
	/**
	 * 接收消息的class类型
	 */
	private Class<?> requestMessageClass;
	
	/**
	 * 发送标识
	 */
	private String sendTag;
	
	/**
	 * 发送消息的class类型
	 */
	private Class<?> sendMessageClass;
	
	/**
	 * 方法对象
	 */
	private Method method;

	public String getRequestTag() {
		return requestTag;
	}

	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}

	public String getSendTag() {
		return sendTag;
	}

	public void setSendTag(String sendTag) {
		this.sendTag = sendTag;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}

	public Class<?> getRequestMessageClass() {
		return requestMessageClass;
	}

	public void setRequestMessageClass(Class<?> requestMessageClass) {
		this.requestMessageClass = requestMessageClass;
	}

	public Class<?> getSendMessageClass() {
		return sendMessageClass;
	}

	public void setSendMessageClass(Class<?> sendMessageClass) {
		this.sendMessageClass = sendMessageClass;
	}
	
	

}
