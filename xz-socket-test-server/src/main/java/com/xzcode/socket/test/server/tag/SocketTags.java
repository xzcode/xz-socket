package com.xzcode.socket.test.server.tag;

public interface SocketTags {
	
	/**
	 * 心跳包
	 * @author zai 2017-08-15 22:39:45
	 */
	interface HeartBeat{
		/**
		 * 心跳 ping
		 */
		int PING = 1;
		
		/**
		 * 心跳 pong
		 */
		int PONG = 2;
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
