package com.xzcode.socket.core.mapper;

import java.util.HashMap;
import java.util.Map;

public class SocketComponentObjectMapper {
	
	private final Map<Class<?>, Object> map = new HashMap<>();
	
	public Object getSocketComponentObject(Class<?> clazz) {
		return map.get(clazz);
	}
	
	public void put(Class<?> key, Object controller) {
		map.put(key, controller);
	}
	
	public Map<Class<?>, Object> getComponentMap() {
		return map;
	}
	
	
	
}
