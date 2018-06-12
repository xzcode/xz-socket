package xz.commons.socket.core.serializer.factory;

import xz.commons.socket.core.serializer.ISerializer;
import xz.commons.socket.core.serializer.impl.JsonSerializer;
import xz.commons.socket.core.serializer.impl.MessagePackSerializer;

/**
 * 序列化器工厂类
 *
 * @author zai
 * 2017-08-12 13:49:53
 */
public class SerializerFactory {
	
	private static final ISerializer JSON_SERIALIZER = new JsonSerializer();
	
	private static final ISerializer MESSAGE_PACK_SERIALIZER = new MessagePackSerializer();
	
	/**
	 * 序列化器类型定义
	 *
	 * @author zai
	 * 2017-08-12 13:47:25
	 */
	public static interface SerializerType{
		
		String JSON = "json";
		
		String MESSAGE_PACK = "msgpack";
		
	}
	
	/**
	 * 获取序列化器
	 * @return 返回指定的序列化器实现，默认为json序列化器
	 * @author zai
	 * 2017-08-12 13:50:07
	 */
	public static ISerializer geSerializer(String serializerType) {
		
		switch (serializerType) {
		
		case SerializerType.JSON:
			return JSON_SERIALIZER;
			
		case SerializerType.MESSAGE_PACK:
			return MESSAGE_PACK_SERIALIZER;
			
		default:
			return JSON_SERIALIZER;
		}
		
	}

}
