package com.xzcode.socket.core.sender;

import com.xzcode.socket.core.sender.callback.SocketSendMessageCallback;

public class SendModel {

	/* 发送消息标识 */
	private String sendTag;

	/* 消息体 */
	private Object message;

	/* io操作完成回调 */
	SocketSendMessageCallback successCallback;

	SocketSendMessageCallback cancelCallback;

	SocketSendMessageCallback doneCallback;

	public SendModel(String sendTag, Object message) {
		this.sendTag = sendTag;
		this.message = message;
	}

	public SendModel(String sendTag, Object message, SocketSendMessageCallback successCallback) {
		super();
		this.sendTag = sendTag;
		this.message = message;
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

	public static SendModel create(String sendTag, Object message, SocketSendMessageCallback successCallback) {
		return new SendModel(sendTag, message, successCallback);
	}

	public static SendModel create(String sendTag, Object message) {
		return new SendModel(sendTag, message);
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

}
