package com.xzcode.socket.core.handler.netty;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.config.SocketServerConfig;
import com.xzcode.socket.core.event.EventMethodInvoker;
import com.xzcode.socket.core.executor.SocketServerTaskExecutor;
import com.xzcode.socket.core.handler.netty.idle.IdleHandler;
import com.xzcode.socket.core.handler.netty.life.InboundLifeCycleHandler;
import com.xzcode.socket.core.handler.netty.life.OutboundLifeCycleHandler;
import com.xzcode.socket.core.handler.netty.web.WebSocketInboundFrameHandler;
import com.xzcode.socket.core.handler.netty.web.WebSocketOutboundFrameHandler;
import com.xzcode.socket.core.message.MessageInvokerManager;
import com.xzcode.socket.core.serializer.factory.SerializerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * WebSocket channel 初始化处理器
 * 
 * 
 * @author zai
 * 2017-08-02
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelInitializer.class);
	
	private SocketServerConfig config;
	
	
	private SocketServerTaskExecutor taskExecutor;
	
	private SslContext sslCtx;
	
	public WebSocketChannelInitializer() {
	}
	
	public WebSocketChannelInitializer(
			SocketServerConfig config,
			SocketServerTaskExecutor taskExecutor, 
			SslContext sslCtx
			) {
		super();
		this.config = config;
		this.taskExecutor = taskExecutor;
		this.sslCtx = sslCtx;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		//Inbound 是顺序执行
		if (sslCtx != null) {
			ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
		}
		/*
		ch.pipeline().addLast(new SslHandler(WebSocketServerStarter.sslEngine));
		*/
	   	if (config.getIdleCheckEnabled()) {
	   		
		   	 //空闲事件触发器
		   	 ch.pipeline().addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.MILLISECONDS));
		   	 
		   	 //心跳包处理
		   	 ch.pipeline().addLast(new IdleHandler(this.taskExecutor,config.getEventMethodInvoker()));
		   	 
	   	}
	   	
	   	ch.pipeline().addLast(new HttpServerCodec());
	   	ch.pipeline().addLast(new HttpObjectAggregator(config.getHttpMaxContentLength()));
	   	//ch.pipeline().addLast(new HttpRequestHandler(config.getWebsocketPath()));
	   	ch.pipeline().addLast(new WebSocketServerCompressionHandler());
	   	ch.pipeline().addLast(new WebSocketServerProtocolHandler(config.getWebsocketPath(), null, true, config.getHttpMaxContentLength(), false, true));
	   	ch.pipeline().addLast("WebSocketInboundFrameHandler",new WebSocketInboundFrameHandler(SerializerFactory.geSerializer(config.getSerializerType()),this.taskExecutor, config.getMessageInvokerManager(), config.getMessageFilterManager()));
	   	//ch.pipeline().addLast(new WebSocketServerHandler());
	   	
	   	//inbound异常处理
	   	ch.pipeline().addLast(new InboundLifeCycleHandler(this.config, taskExecutor, config.getEventMethodInvoker()));
	   	
        
        //Outbound 是反顺序执行
	   	ch.pipeline().addLast("WebSocketOutboundFrameHandler",new WebSocketOutboundFrameHandler(SerializerFactory.geSerializer(config.getSerializerType())));
        
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

	public SslContext getSslCtx() {
		return sslCtx;
	}
	public void setSslCtx(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}
}
