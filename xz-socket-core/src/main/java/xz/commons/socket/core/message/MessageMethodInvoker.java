package xz.commons.socket.core.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xz.commons.socket.core.mapper.SocketComponentObjectMapper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MessageMethodInvoker {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageMethodInvoker.class);

	private final Map<String, RequestMethodModel> map = new HashMap<>();

	private SocketComponentObjectMapper componentObjectMapper;


	public MessageMethodInvoker(SocketComponentObjectMapper componentObjectMapper) {
		super();
		this.componentObjectMapper = componentObjectMapper;
	}

	/**
	 * 调用被缓存的方法
	 * @param requestTag
	 * @param message
	 * @throws Exception
	 *
	 * @author zai
	 * 2017-07-29
	 */
	public Object invoke(String requestTag, Object message) throws Exception {
		Method method = map.get(requestTag).getMethod();
		if (method != null) {
			//消息体为空
			if (message == null) {
				return method.invoke(componentObjectMapper.getSocketComponentObject(method.getDeclaringClass()));
			}
			//消息体不为空
			return method.invoke(componentObjectMapper.getSocketComponentObject(method.getDeclaringClass()), message);
		}
		LOGGER.warn("No method mapped with tag: {} ", requestTag);
		return null;
	}

	/**
	 * 添加缓存方法
	 * @param requestTag
	 *
	 * @author zai
	 * 2017-07-29
	 */
	public void put(String requestTag, RequestMethodModel requestMethodModel) {
		if (map.containsKey(requestTag)) {
			throw new RuntimeException("requestTag {} is already mapped!");
		}
		map.put(requestTag, requestMethodModel);
	}

	/**
	 * 获取返还标识
	 * @param requestTag
	 * @return
	 *
	 * @author zai
	 * 2017-08-02
	 */
	public String getSendTag(String requestTag){
		return get(requestTag).getSendTag();
	}

	/**
	 * 获取关联方法模型
	 * @param requestTag
	 * @return
	 *
	 * @author zai
	 * 2017-08-02
	 */
	public RequestMethodModel get(String requestTag){
		return map.get(requestTag);
	}


	public void setComponentObjectMapper(SocketComponentObjectMapper componentObjectMapper) {
		this.componentObjectMapper = componentObjectMapper;
	}

}
