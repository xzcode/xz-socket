package com.xzcode.game.server.tag;

public interface SocketTags {
	
	/**
	 * 心跳包
	 * @author zai 2017-08-15 22:39:45
	 */
	interface HeartBeat{
		/**
		 * 心跳 ping
		 */
		String PING = "ping";
		
		/**
		 * 心跳 pong
		 */
		String PONG = "pong";
	}
	
	/**
	 * 登录模块标识
	 * 
	 * 
	 * @author zai
	 * 2018-05-23
	 */
	interface Login{
		/**
		 * 服务端登录检查请求
		 */
		int CHECK_LOGIN_S = 101;

		/**
		 * 客户端端登录检查响应
		 */
		int CHECK_LOGIN_C = 102;
		
	}
	

}
