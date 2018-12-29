package com.xzcode.socket.core.handler.netty.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

/**
 * websocket 消息发送处理器
 * 
 * @author zai
 * 2018-12-29 14:01:59
 */
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
			
			byte[] tagBytes = sendModel.getSendTag().getBytes("utf-8");
			
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
				
				ctx.writeAndFlush(new BinaryWebSocketFrame(out));
			}
			
			//添加完成监听
			promise.addListener((future) -> {
				if (future.isSuccess() && sendModel.getSuccessCallback() != null) {
					sendModel.getSuccessCallback().call();
				}
				
			});
			
		}else if(msg instanceof DefaultFullHttpResponse){
			
			super.write(ctx, msg, promise);
			
		}else{
			throw new UnsupportedOperationException("Unsupported outbound data !");
		}
	}

    
    
}
