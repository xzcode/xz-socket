package com.xzcode.socket.test.server.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.xzcode.socket.test.server.tag.SocketTags;

import xz.commons.socket.core.annotation.SocketComponent;
import xz.commons.socket.core.annotation.SocketRequest;
import xz.commons.socket.core.annotation.SocketResponse;

/**
 * 登录控制器
 *
 * @author zai
 * 2017-09-17 12:24:39
 */
@SocketComponent
public class LoginController {
	
	
	
	@SocketRequest(SocketTags.Login.CHECK_LOGIN_S)
	@SocketResponse(SocketTags.Login.CHECK_LOGIN_C)
	public Map<String, Object> checkLogin(Map<String, Object> req) {
		
		String username = (String) req.get("username");
		String password = (String) req.get("password");
		Boolean autoLogin = (Boolean) req.get("autoLogin");
		
		
		Map<String, Object> resp = new LinkedHashMap<>();
		
		return resp;
	}
	
	
}
