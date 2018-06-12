package xz.commons.socket.core.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventMethodModel {
	
	private int eventTag;
	
	private List<Method> methods = new ArrayList<>(1);
	
	

	public int getEventTag() {
		return eventTag;
	}

	public EventMethodModel setEventTag(int eventTag) {
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

