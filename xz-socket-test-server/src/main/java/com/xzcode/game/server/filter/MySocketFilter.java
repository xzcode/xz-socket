package com.xzcode.game.server.filter;

import com.xzcode.socket.core.annotation.SocketComponent;
import com.xzcode.socket.core.annotation.SocketFilter;
import com.xzcode.socket.core.filter.SocketMessageFilter;

@SocketComponent
@SocketFilter(order = 1)
public class MySocketFilter implements SocketMessageFilter {

	@Override
	public boolean doFilter(String requestTag, Object message) {
		System.out.println("requestTag:" + message);
		return true;
	}

}
