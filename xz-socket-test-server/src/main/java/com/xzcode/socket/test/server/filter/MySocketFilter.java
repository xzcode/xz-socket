package com.xzcode.socket.test.server.filter;

import xz.commons.socket.core.annotation.SocketComponent;
import xz.commons.socket.core.annotation.SocketFilter;
import xz.commons.socket.core.filter.SocketMessageFilter;

@SocketComponent
@SocketFilter(order = 1)
public class MySocketFilter implements SocketMessageFilter {

	@Override
	public boolean doFilter(int requestTag, Object message) {
		return true;
	}

}
