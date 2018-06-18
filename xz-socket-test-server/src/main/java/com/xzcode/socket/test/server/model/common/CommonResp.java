package com.xzcode.socket.test.server.model.common;

public class CommonResp {
	
	private boolean resp_success;
	
	private int resp_code;
	
	private String resp_msg;
	
	public CommonResp() {
	}
	
	public CommonResp(boolean resp_success) {
		this.resp_success = resp_success;
	}

	public static final CommonResp success() {
		return new CommonResp(true);
	}
	
	public static final CommonResp fail() {
		return new CommonResp(false);
	}
	
	public final CommonResp code() {
		this.resp_success = true;
		return this;
	}
	
	public CommonResp code(int code) {
		this.resp_code = code;
		return this;
	}
	
	public CommonResp msg(String msg) {
		this.resp_msg = msg;
		return this;
	}

	public boolean isResp_success() {
		return resp_success;
	}

	public void setResp_success(boolean resp_success) {
		this.resp_success = resp_success;
	}

	public int getResp_code() {
		return resp_code;
	}

	public void setResp_code(int resp_code) {
		this.resp_code = resp_code;
	}

	public String getResp_msg() {
		return resp_msg;
	}

	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}

}
