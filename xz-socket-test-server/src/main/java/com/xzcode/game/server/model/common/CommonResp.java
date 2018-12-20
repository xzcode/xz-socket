package com.xzcode.game.server.model.common;

public class CommonResp {
	
	private boolean _success;
	
	private int _code;
	
	private String _msg;
	
	public CommonResp() {
	}
	
	public CommonResp(boolean _success) {
		this._success = _success;
	}

	protected static final CommonResp success() {
		return new CommonResp(true);
	}
	
	protected static final CommonResp fail() {
		return new CommonResp(false);
	}
	
	protected final CommonResp code() {
		this._success = true;
		return this;
	}

	public boolean is_success() {
		return _success;
	}

	public void set_success(boolean _success) {
		this._success = _success;
	}

	public int get_code() {
		return _code;
	}

	public void set_code(int _code) {
		this._code = _code;
	}

	public String get_msg() {
		return _msg;
	}

	public void set_msg(String _msg) {
		this._msg = _msg;
	}
	
	
	

}
