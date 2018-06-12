package xz.commons.socket.core.handler.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xz.commons.socket.core.config.SocketServerConfig;
import xz.commons.socket.core.event.EventMethodInvoker;
import xz.commons.socket.core.executor.SocketServerTaskExecutor;
import xz.commons.socket.core.handler.netty.codec.DecodeHandler;
import xz.commons.socket.core.handler.netty.codec.EncodeHandler;
import xz.commons.socket.core.handler.netty.idle.IdleHandler;
import xz.commons.socket.core.handler.netty.life.InboundLifeCycleHandler;
import xz.commons.socket.core.handler.netty.life.OutboundLifeCycleHandler;
import xz.commons.socket.core.message.MessageMethodInvoker;
import xz.commons.socket.core.serializer.factory.SerializerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 默认channel初始化处理器
 * 
 * 
 * @author zai
 * 2017-08-02
 */
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketChannelInitializer.class);


	private SocketServerConfig config;
	
	
	private SocketServerTaskExecutor taskExecutor;
	
	public SocketChannelInitializer() {
	}
	
	private MessageMethodInvoker messageMethodInvoker;
	
	private EventMethodInvoker eventMethodInvoker;

	public SocketChannelInitializer(
			SocketServerConfig config, 
			SocketServerTaskExecutor taskExecutor,
			MessageMethodInvoker messageMethodInvoker,
			EventMethodInvoker eventMethodInvoker
			) {
		super();
		this.config = config;
		this.taskExecutor = taskExecutor;
		this.messageMethodInvoker = messageMethodInvoker;
		this.eventMethodInvoker = eventMethodInvoker;
	}
	
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		LOGGER.debug("Init Channel:{}", ch);
		
		//Inbound 是顺序执行
   	 
	   	 
	   	 
	   	if (config.getIdleCheckEnabled()) {
	   		
		   	 //空闲事件触发器
		   	 ch.pipeline().addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.MILLISECONDS));
		   	 
		   	 //心跳包处理
		   	 ch.pipeline().addLast(new IdleHandler(this.taskExecutor, this.eventMethodInvoker));
		   	 
	   	}
	   	
   	 
	   	 //消息解码器
        ch.pipeline().addLast(new DecodeHandler(SerializerFactory.geSerializer(config.getSerializerType()), this.taskExecutor, messageMethodInvoker));
        
        //inbound异常处理
        ch.pipeline().addLast(new InboundLifeCycleHandler(this.config, taskExecutor, eventMethodInvoker));
        
        
        //Outbound 是反顺序执行
        
        //消息编码器
        ch.pipeline().addLast(new EncodeHandler(SerializerFactory.geSerializer(config.getSerializerType())));
        //outbound异常处理
        ch.pipeline().addLast(new OutboundLifeCycleHandler());
	}
	

	public SocketServerConfig getConfig() {
		return config;
	}
	
	public void setConfig(SocketServerConfig config) {
		this.config = config;
	}
	
	public SocketServerTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	
	public void setTaskExecutor(SocketServerTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

}
