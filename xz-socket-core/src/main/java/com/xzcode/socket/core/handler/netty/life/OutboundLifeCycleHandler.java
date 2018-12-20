package com.xzcode.socket.core.handler.netty.life;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutboundLifeCycleHandler extends ChannelOutboundHandlerAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(OutboundLifeCycleHandler.class);
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(" Outbound ERROR! ", cause);
		if (cause instanceof UnsupportedOperationException) {
			logger.error("UnsupportedOperationException, channel close ! ", cause);
			ctx.channel().close();
		}
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		logger.error(" Outbound disconnect! ");
		super.disconnect(ctx, promise);
	}
	

}
