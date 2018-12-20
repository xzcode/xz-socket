package com.xzcode.socket.core.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventMethodModel {
	
	private String eventTag;
	
	private List<Method> methods = new ArrayList<>(1);
	
	

	public String getEventTag() {
		return eventTag;
	}

	public EventMethodModel setEventTag(String eventTag) {
		this.eventTag = eventTag;
		return this;
	}

	public List<Method> getMethods() {
		return methods;
	}
	
	public EventMethodModel addMethod(Method method) {
		methods.add(method);
		return this;
	}

	
	
}

