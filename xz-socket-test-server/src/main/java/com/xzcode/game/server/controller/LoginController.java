package com.xzcode.game.server.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.xzcode.game.server.model.common.CommonResp;
import com.xzcode.game.server.model.login.LoginReq;
import com.xzcode.game.server.model.login.LoginResp;
import com.xzcode.socket.core.annotation.SocketComponent;
import com.xzcode.socket.core.annotation.SocketRequest;
import com.xzcode.socket.core.annotation.SocketResponse;
import com.xzcode.socket.core.message.SocketOnMessage;
import com.xzcode.socket.core.utils.SocketServerService;

/**
 * 登录控制器
 *
 * @author zai
 * 2017-09-17 12:24:39
 */
@SocketComponent
public class LoginController {
	
	@Autowired
	private SocketServerService sss;
	
	
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
		loginResp.setSuccess(true);
		
		return loginResp;
	}
	
	@SocketRequest("test")
	public void disconnect() {
		/*
		sss.send("disconnect.call", CommonResp.success().setMsg("关闭连接"), () -> {
			//SocketServerUtil.disconnect();
		});
		int i= 10;
		while (i-- > 0) {
			sss.send("disconnect.call", CommonResp.success().setMsg(i+""));
		}
		*/
		sss.on("login.dis.req", new SocketOnMessage<LoginReq>() {

			@Override
			public void onMessage(LoginReq req) {
				System.out.println("on message: login.dis.req");
				
			}
			
		});
		
	}
	
	
}
