package com.xzcode.game.server.model.common;

public class CommonResp {
	
	private boolean success;
	
	private int code;
	
	private String msg;
	
	public CommonResp() {
	}
	
	public CommonResp(boolean success) {
		this.success = success;
	}

	public static CommonResp success() {
		return new CommonResp(true);
	}
	
	public static CommonResp fail() {
		return new CommonResp(false);
	}
	
	public CommonResp setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}
	
	
	

}
