package com.xzcode.socket.test.server.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.xzcode.socket.core.annotation.SocketComponent;
import com.xzcode.socket.core.annotation.SocketRequest;
import com.xzcode.socket.core.annotation.SocketResponse;
import com.xzcode.socket.core.utils.SocketServerUtil;

/**
 * 登录控制器
 *
 * @author zai
 * 2017-09-17 12:24:39
 */
@SocketComponent
public class LoginController {
	
	
	
	@SocketRequest("s.check.login")
	@SocketResponse("c.check.login")
	public Map<String, Object> checkLogin(Map<String, Object> req) {
		
		String username = (String) req.get("username");
		String password = (String) req.get("password");
		Boolean autoLogin = (Boolean) req.get("autoLogin");
		
		
		Map<String, Object> resp = new LinkedHashMap<>();
		resp.put("username", username);
		resp.put("password", password);
		
		return resp;
	}
	
	
}
