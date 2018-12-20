package com.xzcode.socket.core.handler.netty.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xzcode.socket.core.sender.SendModel;
import com.xzcode.socket.core.serializer.ISerializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketOutboundFrameHandler extends ChannelOutboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketOutboundFrameHandler.class);
    
    private ISerializer serializer;
    
    private static final Gson GSON = new GsonBuilder()
    		.serializeNulls()
    		.create();
    
    public WebSocketOutboundFrameHandler() {
    	
	}
    
    
    

	public WebSocketOutboundFrameHandler(ISerializer serializer) {
		super();
		this.serializer = serializer;
	}




	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		
		if (msg instanceof SendModel) {
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sending message:{}", GSON.toJson(msg));
			}
			
			SendModel sendModel = (SendModel) msg;
			
			byte[] tagBytes = this.serializer.serialize(sendModel.getSendTag());
			
			//如果有消息体
			if (sendModel.getMessage() != null) {
				
				byte[] bodyBytes = this.serializer.serialize(sendModel.getMessage());
				
				ByteBuf out = ctx.alloc().buffer(2 + tagBytes.length + bodyBytes.length);
				
				out.writeShort(tagBytes.length);
				out.writeBytes(tagBytes);
				out.writeBytes(bodyBytes);
				
				ctx.write(new BinaryWebSocketFrame(out));
				
			} else {
			
				//如果没消息体
				
				ByteBuf out = ctx.alloc().buffer(2 + tagBytes.length);
				
				out.writeShort(tagBytes.length);
				out.writeBytes(tagBytes);
				
				ctx.write(new BinaryWebSocketFrame(out));
			
			}
			
		}else if(msg instanceof DefaultFullHttpResponse){
			
			super.write(ctx, msg, promise);
			
		}else{
			throw new UnsupportedOperationException("Unsupported outbound data !");
		}
	}

    
    
}
