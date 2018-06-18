package xz.commons.socket.core.starter.impl;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xz.commons.socket.core.config.SocketServerConfig;
import xz.commons.socket.core.config.scanner.SocketComponentScanner;
import xz.commons.socket.core.executor.SocketServerTaskExecutor;
import xz.commons.socket.core.executor.factory.EventLoopGroupThreadFactory;
import xz.commons.socket.core.handler.netty.WebSocketChannelInitializer;
import xz.commons.socket.core.starter.SocketServerStarter;

public class WebSocketServerStarter implements SocketServerStarter {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServerStarter.class);
	
	private SocketServerConfig config;
	
	private EventLoopGroup bossGroup;
	
	private EventLoopGroup workerGroup;
	
	
	private SocketServerTaskExecutor taskExecutor;
	
    
    private SslContext sslCtx;
    
    public WebSocketServerStarter(SocketServerConfig config) {
    	
    	if (config.isUseSSL()) {
	    	try {
	    	 // Configure SSL.
		        if (config.isUseSSL()) {
		            SelfSignedCertificate ssc = new SelfSignedCertificate();
		            System.out.println("ssc.certificate():\n" + ssc.certificate());
		            System.out.println("ssc.privateKey():\n" + ssc.privateKey());
		            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
		        } else {
		            sslCtx = null;
		        }
	    	} catch (Exception e) {
	    		logger.error("SSL error!", e);
			}
    	}
    	
    	config.setServerType(SocketServerConfig.ServerTypeConstants.WEBSOCKET);
    	this.config = config;
    	
        this.taskExecutor = new SocketServerTaskExecutor(config);
        
        SocketComponentScanner.scan(
        		config.getComponentObjectMapper(),
        		config.getMessageMethodInvokeMapper(),
        		config.getEventMethodInvokeMapper(),
        		config.getScanPackage()
        		);
    }
    
    public SocketServerStarter run() {
    	
        bossGroup = new NioEventLoopGroup(config.getBossThreadSize(),new EventLoopGroupThreadFactory("Socket Boss Group"));// (1)
        
        workerGroup = new NioEventLoopGroup(config.getBossThreadSize(),new EventLoopGroupThreadFactory("Socket Worker Group"));
        
        try {
        	
            ServerBootstrap boot = new ServerBootstrap(); // (2)
            
            //设置工作线程组
            boot.group(bossGroup, workerGroup);
            
            boot.handler(new LoggingHandler(LogLevel.INFO));
            
            //设置channel类型
            boot.channel(NioServerSocketChannel.class); // (3)
            
            //设置消息处理器
            boot.childHandler(new WebSocketChannelInitializer(
            		config, 
            		taskExecutor,
            		config.getMessageMethodInvokeMapper(),
            		config.getEventMethodInvokeMapper(),
            		sslCtx
            		));
            
            boot.option(ChannelOption.SO_BACKLOG, 128);         // (5)
            boot.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
    
            // 绑定端口并开始接受连接，此时线程将阻塞不会继续往下执行
            ChannelFuture f = boot.bind(config.getPort()).sync(); // (7)
    
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        }catch (Exception e) {
        	throw new RuntimeException("Socket server start failed !! ", e);
		} finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        return this;
    }
    
    /**
     * 关闭socket server
     * 
     * 
     * @author zai
     * 2017-07-27
     */
    public SocketServerStarter shutdown() {
    	if (workerGroup != null) {
    		workerGroup.shutdownGracefully();			
		}
    	if (bossGroup != null) {
    		bossGroup.shutdownGracefully();			
		}
        return this;
	}
    
    public void setConfig(SocketServerConfig config) {
		this.config = config;
	}
    public SocketServerConfig getConfig() {
		return config;
	}
    
    public static void main(String[] args) throws Exception {
    	
		new WebSocketServerStarter(new SocketServerConfig()).run();
	}
}
