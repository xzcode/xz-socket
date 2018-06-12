package xz.commons.socket.core.handler.netty.life;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xz.commons.socket.core.channel.DefaultAttributeKeys;
import xz.commons.socket.core.channel.SocketChannelGroups;
import xz.commons.socket.core.config.SocketServerConfig;
import xz.commons.socket.core.event.EventMethodInvoker;
import xz.commons.socket.core.event.SocketEventTask;
import xz.commons.socket.core.event.SocketEvents;
import xz.commons.socket.core.executor.SocketServerTaskExecutor;
import xz.commons.socket.core.session.UserSessonMapper;
import xz.commons.socket.core.session.imp.SocketSession;

import java.net.InetSocketAddress;

public class InboundLifeCycleHandler extends ChannelInboundHandlerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InboundLifeCycleHandler.class);
	
	private SocketServerTaskExecutor taskExecutor;
	
	private SocketServerConfig config;
	
	private EventMethodInvoker eventMethodInvoker;
	
	public InboundLifeCycleHandler() {
	}
	
	
	
	public InboundLifeCycleHandler(SocketServerConfig config) {
		super();
		this.config = config;
	}
	
	public InboundLifeCycleHandler(SocketServerConfig config, SocketServerTaskExecutor taskExecutor, EventMethodInvoker eventMethodInvoker) {
		super();
		this.config = config;
		this.taskExecutor = taskExecutor;
		this.eventMethodInvoker = eventMethodInvoker;
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LOGGER.error("Inbound ERROR! ", cause);
		super.exceptionCaught(ctx, cause);
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
		
		taskExecutor.submit(new SocketEventTask(session, SocketEvents.ChannelState.ACTIVE, eventMethodInvoker));
		
		super.channelActive(ctx);
	}
	

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Channel Inactive:{}", ctx.channel());
		}
		SocketSession session = ctx.channel().attr(DefaultAttributeKeys.SESSION).getAndSet(null);
		
		session.inActive();
		
		taskExecutor.submit(new SocketEventTask(session, SocketEvents.ChannelState.INACTIVE, eventMethodInvoker));
		
		//移除全局channelgroup绑定
		SocketChannelGroups.getGlobalGroup().remove(ctx.channel());
		
		UserSessonMapper.remove(session.getRegisteredUserId());
		
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
