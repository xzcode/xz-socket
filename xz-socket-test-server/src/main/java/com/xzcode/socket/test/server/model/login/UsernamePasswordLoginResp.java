package com.xzcode.socket.test.server.model.login;

import com.xzcode.socket.test.server.model.common.CommonResp;

public class UsernamePasswordLoginResp extends CommonResp{
	
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}
