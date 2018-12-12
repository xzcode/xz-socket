package xz.commons.socket.core.event;

import xz.commons.socket.core.mapper.SocketComponentObjectMapper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventMethodInvoker {
	
	private SocketComponentObjectMapper componentObjectMapper;
	
	
	private final Map<String, EventMethodModel> map = new HashMap<>();
	
	
	
	
	public EventMethodInvoker(SocketComponentObjectMapper componentObjectMapper) {
		super();
		this.componentObjectMapper = componentObjectMapper;
	}

	/**
	 * 调用被缓存的方法
	 * @param requestTag
	 * @param method
	 * @param message
	 * @throws Exception
	 * 
	 * @author zai
	 * 2017-07-29
	 */
	public void invoke(String eventTag) throws Exception {
		EventMethodModel eventMethodModel = map.get(eventTag);
		if (eventMethodModel != null) {
			Method method = null;
			for (int i = 0; i < eventMethodModel.getMethods().size(); i++) {
				method = eventMethodModel.getMethods().get(i);
				method.invoke(componentObjectMapper.getSocketComponentObject(method.getDeclaringClass()));
			}
		}
	}
	
	/**
	 * 添加缓存方法
	 * @param model
	 * 
	 * @author zai
	 * 2017-07-29
	 */
	public void put(EventMethodModel model) {
		EventMethodModel methodModel = map.get(model.getEventTag());
		if (methodModel != null) {
			methodModel.getMethods().addAll(model.getMethods());
			return;
		}
		map.put(model.getEventTag(), model);
	}
	
	
	/**
	 * 获取关联方法模型
	 * @param eventTag
	 * @return
	 * 
	 * @author zai
	 * 2017-08-02
	 */
	public EventMethodModel get(String eventTag){
		return map.get(eventTag);
	}
	
	/**
	 * 是否存在指定事件类型
	 * @param eventTag
	 * @return
	 * 
	 * @author zai
	 * 2018-05-29
	 */
	public boolean contains(String eventTag){
		return map.containsKey(eventTag);
	}
	
	

}
