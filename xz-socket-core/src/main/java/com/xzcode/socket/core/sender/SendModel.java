package com.xzcode.socket.core.sender;

import com.google.gson.annotations.Expose;
import com.xzcode.socket.core.sender.callback.SocketSendMessageCallback;
import com.xzcode.socket.core.session.imp.SocketSession;

public class SendModel {

	/* 发送消息标识 */
	private String sendTag;

	/* 消息体 */
	private Object message;
	
	/*sesson*/
	@Expose
	private SocketSession session;

	/* io操作完成回调 */
	@Expose
	SocketSendMessageCallback successCallback;
	
	@Expose
	SocketSendMessageCallback cancelCallback;
	
	@Expose
	SocketSendMessageCallback doneCallback;

	public SendModel(String sendTag, Object message, SocketSession session) {
		this.sendTag = sendTag;
		this.message = message;
		this.session = session;
	}

	public SendModel(String sendTag, Object message, SocketSendMessageCallback successCallback, SocketSession session) {
		super();
		this.sendTag = sendTag;
		this.message = message;
		this.session = session;
		this.successCallback = successCallback;
	}

	public SendModel(String sendTag, Object message, SocketSendMessageCallback successCallback, SocketSendMessageCallback doneCallback) {
		super();
		this.sendTag = sendTag;
		this.message = message;
		this.successCallback = successCallback;
		this.doneCallback = doneCallback;
	}

	public SocketSendMessageCallback getSuccessCallback() {
		return successCallback;
	}

	public void setSuccessCallback(SocketSendMessageCallback successCallback) {
		this.successCallback = successCallback;
	}

	public SocketSendMessageCallback getDoneCallback() {
		return doneCallback;
	}

	public void setDoneCallback(SocketSendMessageCallback doneCallback) {
		this.doneCallback = doneCallback;
	}

	public static SendModel create(String sendTag, Object message, SocketSendMessageCallback successCallback, SocketSession session) {
		return new SendModel(sendTag, message, successCallback,session);
	}

	public static SendModel create(String sendTag, Object message, SocketSession session) {
		return new SendModel(sendTag, message,session);
	}

	public String getSendTag() {
		return sendTag;
	}

	public void setSendTag(String sendTag) {
		this.sendTag = sendTag;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public SocketSendMessageCallback getCallback() {
		return successCallback;
	}

	public void setCallback(SocketSendMessageCallback callback) {
		this.successCallback = callback;
	}
	
	public SocketSession getSession() {
		return session;
	}
	
	public void setSession(SocketSession session) {
		this.session = session;
	}

}
