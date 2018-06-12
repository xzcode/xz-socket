package xz.commons.socket.core.handler.netty.idle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xz.commons.socket.core.channel.DefaultAttributeKeys;
import xz.commons.socket.core.event.EventMethodInvoker;
import xz.commons.socket.core.event.SocketEventTask;
import xz.commons.socket.core.event.SocketEvents;
import xz.commons.socket.core.executor.SocketServerTaskExecutor;
import xz.commons.socket.core.session.imp.SocketSession;

/**
 * 心跳包触发器
 *
 * @author zai
 * 2017-08-04 23:23:06
 */
public class IdleHandler extends ChannelInboundHandlerAdapter{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(IdleHandler.class);

	private SocketServerTaskExecutor taskExecutor;
	
	private EventMethodInvoker eventMethodInvoker;
	
	private boolean readerIdleEnabled;
	
	private boolean writerIdleEnabled;
	
	private boolean allIdleEnabled;
	
	public IdleHandler() {
		init();
	}
	
	
	public IdleHandler(SocketServerTaskExecutor taskExecutor, EventMethodInvoker eventMethodInvoker) {
		super();
		this.taskExecutor = taskExecutor;
		this.eventMethodInvoker = eventMethodInvoker;
		init();
	}
	
	public void init() {
		checkIdleEventMapped();
	}
	
	public void checkIdleEventMapped() {
		if(eventMethodInvoker.contains(SocketEvents.IdleState.WRITER_IDLE)) {
			this.writerIdleEnabled = true;
		}
		
		if (eventMethodInvoker.contains(SocketEvents.IdleState.READER_IDLE)) {
			this.readerIdleEnabled = true;
		}
		
		if (eventMethodInvoker.contains(SocketEvents.IdleState.ALL_IDLE)) {
			this.allIdleEnabled = true;
		}
		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		
		if (evt instanceof IdleStateEvent) {
			//LOGGER.debug("Socket Event Triggered: {} ", evt);
			SocketSession session = ctx.channel().attr(DefaultAttributeKeys.SESSION).get();
            switch (((IdleStateEvent) evt).state()) {
            	
				case WRITER_IDLE:
						if(writerIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...WRITER_IDLE...: channel:{}", ctx.channel());								
							}
							taskExecutor.submit(new SocketEventTask(session, SocketEvents.IdleState.WRITER_IDLE, eventMethodInvoker));
						}
					break;
				case READER_IDLE:
						if (readerIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...READER_IDLE...: channel:{}", ctx.channel());								
							}
							taskExecutor.submit(new SocketEventTask(session, SocketEvents.IdleState.READER_IDLE, eventMethodInvoker));
						}
					break;
				case ALL_IDLE:
						if (allIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...ALL_IDLE...: channel:{}", ctx.channel());								
							}
							taskExecutor.submit(new SocketEventTask(session, SocketEvents.IdleState.ALL_IDLE, eventMethodInvoker));
						}
					break;
				default:
					break;
					
			}
            
        } else {  
            super.userEventTriggered(ctx, evt);  
        } 
		
	}

	public SocketServerTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	
	public void setTaskExecutor(SocketServerTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
}
