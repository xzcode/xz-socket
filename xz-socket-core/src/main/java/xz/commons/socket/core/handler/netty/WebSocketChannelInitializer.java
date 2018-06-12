package xz.commons.socket.core.handler.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xz.commons.socket.core.config.SocketServerConfig;
import xz.commons.socket.core.event.EventMethodInvoker;
import xz.commons.socket.core.executor.SocketServerTaskExecutor;
import xz.commons.socket.core.handler.netty.http.WebSocketInboundFrameHandler;
import xz.commons.socket.core.handler.netty.idle.IdleHandler;
import xz.commons.socket.core.handler.netty.life.InboundLifeCycleHandler;
import xz.commons.socket.core.handler.netty.life.OutboundLifeCycleHandler;
import xz.commons.socket.core.message.MessageMethodInvoker;
import xz.commons.socket.core.serializer.factory.SerializerFactory;

import java.util.concurrent.TimeUnit;

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
	
	private MessageMethodInvoker messageMethodInvoker;
	
	private EventMethodInvoker eventMethodInvoker;
	
	private SslContext sslCtx;
	
	public WebSocketChannelInitializer() {
	}
	
	public WebSocketChannelInitializer(
			SocketServerConfig config,
			SocketServerTaskExecutor taskExecutor, 
			MessageMethodInvoker messageMethodInvoker,
			EventMethodInvoker eventMethodInvoker,
			SslContext sslCtx
			) {
		super();
		this.config = config;
		this.taskExecutor = taskExecutor;
		this.messageMethodInvoker = messageMethodInvoker;
		this.eventMethodInvoker = eventMethodInvoker;
		this.sslCtx = sslCtx;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		//Inbound 是顺序执行
		
		if (sslCtx != null) {
			ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
		}
   	 	
	   	if (config.getIdleCheckEnabled()) {
	   		
		   	 //空闲事件触发器
		   	 ch.pipeline().addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.MILLISECONDS));
		   	 
		   	 //心跳包处理
		   	 ch.pipeline().addLast(new IdleHandler(this.taskExecutor,this.eventMethodInvoker));
		   	 
	   	}
	   	
	   	ch.pipeline().addLast(new HttpServerCodec());
	   	ch.pipeline().addLast(new HttpObjectAggregator(config.getHttpMaxContentLength()));
	   	//ch.pipeline().addLast(new HttpRequestHandler(config.getWebsocketPath()));
	   	//ch.pipeline().addLast(new WebSocketServerCompressionHandler());
	   	ch.pipeline().addLast(new WebSocketServerProtocolHandler(config.getWebsocketPath(), null, true, config.getHttpMaxContentLength(), false, true));
	   	ch.pipeline().addLast("WebSocketInboundFrameHandler",new WebSocketInboundFrameHandler(SerializerFactory.geSerializer(config.getSerializerType()),this.taskExecutor, messageMethodInvoker));
	   	
	   	
	   	//inbound异常处理
	   	ch.pipeline().addLast(new InboundLifeCycleHandler(this.config, taskExecutor, eventMethodInvoker));
	   	
	   	//ch.pipeline().addLast(new WebSocketOutboundFrameHandler());
        
        //Outbound 是反顺序执行
        
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
