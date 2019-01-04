package com.xzcode.socket.core.handler.netty.life;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.channel.DefaultAttributeKeys;
import com.xzcode.socket.core.channel.SocketChannelGroups;
import com.xzcode.socket.core.config.SocketServerConfig;
import com.xzcode.socket.core.event.EventInvokerManager;
import com.xzcode.socket.core.event.SocketEventTask;
import com.xzcode.socket.core.event.SocketEvents;
import com.xzcode.socket.core.executor.SocketServerTaskExecutor;
import com.xzcode.socket.core.session.UserSessonManager;
import com.xzcode.socket.core.session.imp.SocketSession;

import java.net.InetSocketAddress;

public class InboundLifeCycleHandler extends ChannelInboundHandlerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InboundLifeCycleHandler.class);
	
	
	private SocketServerConfig config;
	
	public InboundLifeCycleHandler() {
	}
	
	
	
	public InboundLifeCycleHandler(SocketServerConfig config) {
		super();
		this.config = config;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LOGGER.error("Inbound ERROR! ", cause);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Channel Active:{}", ctx.channel());
		}
		InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		
		//初始化session
		SocketSession session = new SocketSession(
				ctx.channel(), 
				socketAddress.getHostString(), 
				socketAddress.getPort()
				);
		
		ctx.channel().attr(DefaultAttributeKeys.SESSION).set(session);
		
		//添加到全局channelgroup绑定
		SocketChannelGroups.getGlobalGroup().add(ctx.channel());
		
		config.getTaskExecutor().submit(new SocketEventTask(session, SocketEvents.ChannelState.ACTIVE, config.getEventInvokerManager()));
		
		super.channelActive(ctx);
	}
	

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Channel Inactive:{}", ctx.channel());
		}
		SocketSession session = ctx.channel().attr(DefaultAttributeKeys.SESSION).getAndSet(null);
		
		session.inActive();
		
		config.getTaskExecutor().submit(new SocketEventTask(session, SocketEvents.ChannelState.INACTIVE, config.getEventInvokerManager()));
		
		//移除全局channelgroup绑定
		SocketChannelGroups.getGlobalGroup().remove(ctx.channel());
		
		config.getUserSessonManager().remove(session.getRegisteredUserId());
		
		SocketChannelGroups.getRegisteredGroup().remove(ctx.channel());
		
		super.channelInactive(ctx);
	}


	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Channel Unregistered:{}", ctx.channel());
		}
		super.channelUnregistered(ctx);
	}
	
	
	
	
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("userEventTriggered:{}", evt);			
		}
		
		super.userEventTriggered(ctx, evt);
	}
	
	



	public SocketServerConfig getConfig() {
		return config;
	}
	
	public void setConfig(SocketServerConfig config) {
		this.config = config;
	}

}
