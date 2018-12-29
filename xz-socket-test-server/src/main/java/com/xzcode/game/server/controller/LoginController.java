package com.xzcode.game.server.controller;

import org.springframework.stereotype.Component;

import com.xzcode.game.server.model.login.LoginReq;
import com.xzcode.game.server.model.login.LoginResp;
import com.xzcode.socket.core.annotation.SocketComponent;
import com.xzcode.socket.core.annotation.SocketRequest;
import com.xzcode.socket.core.annotation.SocketResponse;

/**
 * 登录控制器
 *
 * @author zai
 * 2017-09-17 12:24:39
 */
@SocketComponent
public class LoginController {
	
	
	/**
	 * 登录校验
	 *
	 * @param req
	 * @return
	 * @author zai
	 * 2018-12-20 17:59:59
	 */
	@SocketRequest("login.req")
	@SocketResponse("login.resp")
	public LoginResp checkLogin(LoginReq req) {
		
		String sid = req.getSid();
		
		System.out.println("SID:" + sid);
		
		LoginResp loginResp = new LoginResp();
		loginResp.set_success(true);
		
		return loginResp;
	}
	
	
}
