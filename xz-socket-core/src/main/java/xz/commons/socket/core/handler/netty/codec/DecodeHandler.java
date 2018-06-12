package xz.commons.socket.core.handler.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xz.commons.socket.core.channel.DefaultAttributeKeys;
import xz.commons.socket.core.executor.SocketServerTaskExecutor;
import xz.commons.socket.core.message.MessageMethodInvoker;
import xz.commons.socket.core.message.RequestMethodModel;
import xz.commons.socket.core.message.SocketRequestTask;
import xz.commons.socket.core.serializer.ISerializer;

import java.util.List;

public class DecodeHandler extends ByteToMessageDecoder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DecodeHandler.class);

	/**
	 * 数据包长度标识 字节数
	 */
	public static final int PACKAGE_LENGTH_BYTES = 4;
	
	/**
	 * 每个数据包最大长度
	 */
	public static final int MAX_PACKAGE_LENGTH = 65536;
	
	/**
	 * 请求标识 字节数
	 */
	public static final int REQUEST_TAG_BYTES = 4;
	
	/**
	 * 请求头 总字节数
	 */
	public static final int HEADER_BYTES = PACKAGE_LENGTH_BYTES + REQUEST_TAG_BYTES;
	
	/**
	 * 序列化工具类
	 */
	private ISerializer serializer;
	
	private SocketServerTaskExecutor executor;
	
	private MessageMethodInvoker messageMethodInvoker;

	
	public DecodeHandler() {
	}
	
	public DecodeHandler(ISerializer serializer, SocketServerTaskExecutor executor, MessageMethodInvoker messageMethodInvoker) {
		super();
		this.serializer = serializer;
		this.executor = executor;
		this.messageMethodInvoker = messageMethodInvoker;
	}


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		int readableBytes = in.readableBytes();
		//如果长度不足4位，放弃并等待下次读取
		if (readableBytes < HEADER_BYTES) {
			return;
		}
		
		in.markReaderIndex();
		
		int packLen = in.readInt();
		
		if (packLen > MAX_PACKAGE_LENGTH) {
			LOGGER.error("Package length {} is over limit {} ! Channel close !", packLen, MAX_PACKAGE_LENGTH);
			ctx.channel().close();
			return;
		}
		
		//readableBytes = in.readableBytes();
		
		if (readableBytes - PACKAGE_LENGTH_BYTES < packLen) {
			in.resetReaderIndex();
			return;
		}
		
		int reqTag = in.readInt();
		
		byte[] data = new byte[readableBytes - HEADER_BYTES];
		
		//读取数据体部分byte数组
		in.getBytes(0, data);
		
		//获取与请求标识关联的方法参数对象
		RequestMethodModel methodModel = messageMethodInvoker.get(reqTag);
		
		if (methodModel != null && methodModel.getRequestMessageClass() != null) {
			executor.submit(new SocketRequestTask(reqTag, ctx.channel().attr(DefaultAttributeKeys.SESSION).get(),serializer.deserialize(data, methodModel.getRequestMessageClass()),this.messageMethodInvoker));
		}
		
		
		
	}
	
	
	public ISerializer getSerializer() {
		return serializer;
	}
	
	public void setSerializer(ISerializer serializer) {
		this.serializer = serializer;
	}
	
	public void setExecutor(SocketServerTaskExecutor executor) {
		this.executor = executor;
	}
	
	public SocketServerTaskExecutor getExecutor() {
		return executor;
	}


}
