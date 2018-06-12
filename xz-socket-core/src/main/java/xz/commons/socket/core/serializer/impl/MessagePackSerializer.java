package xz.commons.socket.core.serializer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import xz.commons.socket.core.serializer.ISerializer;

/**
 * 基于MessagePack序列化库的实现
 * 
 * 
 * @author zai
 * 2017-07-28
 */
public class MessagePackSerializer implements ISerializer {
	
	private static final ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
	

	@Override
	public byte[] serialize(Object object) throws Exception {
		  return objectMapper.writeValueAsBytes(object);
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> t) throws Exception {
		  return objectMapper.readValue(bytes, t);
	}

}
