package xz.commons.socket.core.handler.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xz.commons.socket.core.sender.SendModel;
import xz.commons.socket.core.serializer.ISerializer;

public class EncodeHandler extends MessageToByteEncoder<SendModel> {


	/**
	 * 序列化工具类
	 */
	private ISerializer serializer;
	
	
	public EncodeHandler() {
	}
	
	
	public EncodeHandler(ISerializer serializer) {
		super();
		this.serializer = serializer;
	}



	@Override
	protected void encode(ChannelHandlerContext ctx, SendModel sendModel, ByteBuf out) throws Exception {
		
		int sendTag = sendModel.getSendTag();
		byte[] serialize = this.serializer.serialize(sendModel.getMessage());
		
		out.writeInt(4 + serialize.length);
		out.writeInt(sendTag);
		out.writeBytes(serialize);
		
	}

	public void setSerializer(ISerializer serializer) {
		this.serializer = serializer;
	}
	
	public ISerializer getSerializer() {
		return serializer;
	}

}
