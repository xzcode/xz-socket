package com.xzcode.socket.core.session;

import java.util.concurrent.ConcurrentHashMap;

import com.xzcode.socket.core.session.imp.SocketSession;

/**
 * 用户id与session关联
 * 
 * 
 * @author zai 2017-07-30 23:11:44
 */
public class UserSessonMapper {
	
	private static final ConcurrentHashMap<Object, SocketSession> map = new ConcurrentHashMap<>();
	
	
	public static void put(Object userId, SocketSession session) {
		map.put(userId, session);
	}
	
	public static SocketSession get(Object userId) {
		if (userId != null) {
			return map.get(userId);
		}
		return null;
	}
	
	public static SocketSession remove(Object userId) {
		if (userId != null) {
			return map.remove(userId);
		}
		return null;
	}

}
