package xz.commons.socket.core.handler.netty.http;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xz.commons.socket.core.sender.SendModel;
import xz.commons.socket.core.serializer.ISerializer;

public class WebSocketOutboundFrameHandler extends ChannelOutboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketOutboundFrameHandler.class);
    
    private ISerializer serializer;
    
    private static final Gson GSON = new Gson();
    
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
				LOGGER.debug("Sending message:", GSON.toJson(msg));
			}
			
			SendModel sendModel = (SendModel) msg;
			String sendTag = sendModel.getSendTag();
			
			if (sendModel.getMessage() != null) {
				
				byte[] bytes = serializer.serialize(sendModel.getMessage());
				ByteBuf out = ctx.alloc().buffer(4 + sendTag.length() + bytes.length);
				
				out.writeInt(sendTag.length());
				out.writeBytes(sendTag.getBytes());
				out.writeBytes(bytes);
				
				super.write(ctx, new BinaryWebSocketFrame(out), promise);
				
				return;
			}
			
			
			ByteBuf out = ctx.alloc().buffer(4 + sendTag.length());
			out.writeInt(sendTag.length());
			out.writeBytes(sendTag.getBytes());
			super.write(ctx, new BinaryWebSocketFrame(out), promise);
			
			
		}else if(msg instanceof DefaultFullHttpResponse){
			
			super.write(ctx, msg, promise);
			
		}else{
			throw new UnsupportedOperationException("Unsupported outbound data !");
		}
	}

    
    
}
