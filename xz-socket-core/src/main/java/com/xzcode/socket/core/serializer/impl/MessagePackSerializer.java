package com.xzcode.socket.core.serializer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzcode.socket.core.serializer.ISerializer;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.jackson.dataformat.MessagePackFactory;

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
		if (object instanceof String) {
			String string =  (String) object;  
			MessageBufferPacker packer = new MessagePack.PackerConfig()
					.withSmallStringOptimizationThreshold(256) // String
					.withBufferSize(256)
		            .newBufferPacker();
            packer.packString(string);
            packer.close();
            return packer.toByteArray(); 
		}
	    return objectMapper.writeValueAsBytes(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> t) throws Exception {
			if (t == String.class) {
				MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bytes);
	            String string =  unpacker.unpackString();  
            	unpacker.close();
            	return (T) string;
			}
		  return objectMapper.readValue(bytes, t);
	}

}
