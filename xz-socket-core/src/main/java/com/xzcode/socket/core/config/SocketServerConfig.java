package com.xzcode.socket.core.config;

import java.util.Arrays;

import com.xzcode.socket.core.event.EventMethodInvoker;
import com.xzcode.socket.core.mapper.SocketComponentObjectMapper;
import com.xzcode.socket.core.message.MessageMethodInvoker;
import com.xzcode.socket.core.serializer.factory.SerializerFactory;

/**
 * socket 服务器配置类
 *
 * @author zai
 * 2017-08-13 19:03:31
 */
public class SocketServerConfig {
	
	private boolean 	enabled = false;

	private boolean 	autoRun = false;

	private String 		host = "0.0.0.0";

	private int 		port = 9999;

	private int 		bossThreadSize = 0;
	private int 		workThreadSize = 0;

	private int 		corePoolSize = 10;
	private int 		maximumPoolSize = 100;
	private long 		keepAliveTime = 10000;
	private int 		taskQueueSize = 1000;

	private String[] 	scanPackage;

	private boolean 	idleCheckEnabled = true;
	private long 		readerIdleTime = 5000;
	private long 		writerIdleTime = 5000;
	private long 		allIdleTime = 5000;

	private String 		serverType = ServerTypeConstants.SOCKET;

	private String 		serializerType = SerializerFactory.SerializerType.MESSAGE_PACK;

	private String 		websocketPath = "/websocket";
	
	private int 		httpMaxContentLength = 65536;
	
	private boolean		useSSL = false;
	
	private SocketComponentObjectMapper componentObjectMapper = new SocketComponentObjectMapper();
	private MessageMethodInvoker messageMethodInvoker = new MessageMethodInvoker(componentObjectMapper);
	private EventMethodInvoker eventMethodInvoker = new EventMethodInvoker(componentObjectMapper);
	
	
	public static interface ServerTypeConstants{
		
		String SOCKET = "socket";
		
		String WEBSOCKET = "websocket";
		
	}
	
	
	
	
	public boolean isEnabled() {
		return enabled;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isAutoRun() {
		return autoRun;
	}
	public void setAutoRun(boolean autoRun) {
		this.autoRun = autoRun;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getBossThreadSize() {
		return bossThreadSize;
	}
	public void setBossThreadSize(int bossThreadSize) {
		this.bossThreadSize = bossThreadSize;
	}
	public int getWorkThreadSize() {
		return workThreadSize;
	}
	public void setWorkThreadSize(int workThreadSize) {
		this.workThreadSize = workThreadSize;
	}
	public int getCorePoolSize() {
		return corePoolSize;
	}
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}
	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
	public long getKeepAliveTime() {
		return keepAliveTime;
	}
	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public int getTaskQueueSize() {
		return taskQueueSize;
	}
	
	public void setTaskQueueSize(int taskQueueSize) {
		this.taskQueueSize = taskQueueSize;
	}
	
	public String[] getScanPackage() {
		return scanPackage;
	}
	
	public void setScanPackage(String...scanPackage) {
		this.scanPackage = scanPackage;
	}
	public long getReaderIdleTime() {
		return readerIdleTime;
	}
	public void setReaderIdleTime(long readerIdleTime) {
		this.readerIdleTime = readerIdleTime;
	}
	public long getWriterIdleTime() {
		return writerIdleTime;
	}
	public void setWriterIdleTime(long writerIdleTime) {
		this.writerIdleTime = writerIdleTime;
	}
	public long getAllIdleTime() {
		return allIdleTime;
	}
	public void setAllIdleTime(long allIdleTime) {
		this.allIdleTime = allIdleTime;
	}
	public boolean isIdleCheckEnabled() {
		return idleCheckEnabled;
	}
	public boolean getIdleCheckEnabled() {
		return idleCheckEnabled;
	}
	public void setIdleCheckEnabled(boolean idleCheckEnabled) {
		this.idleCheckEnabled = idleCheckEnabled;
	}
	
	
	public String getServerType() {
		return serverType;
	}
	
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	
	public String getWebsocketPath() {
		return websocketPath;
	}
	
	public void setWebsocketPath(String websocketPath) {
		this.websocketPath = websocketPath;
	}
	
	public int getHttpMaxContentLength() {
		return httpMaxContentLength;
	}
	
	public void setHttpMaxContentLength(int httpMaxContentLength) {
		this.httpMaxContentLength = httpMaxContentLength;
	}
	
	public String getSerializerType() {
		return serializerType;
	}
	
	public void setSerializerType(String serializerType) {
		if (serializerType == null || serializerType == "") {
			return;
		}
		this.serializerType = serializerType;
	}
	
	@Override
	public String toString() {
		return "SocketServerConfig [enabled=" + enabled + ", autoRun=" + autoRun + ", host=" + host + ", port=" + port
				+ ", bossThreadSize=" + bossThreadSize + ", workThreadSize=" + workThreadSize + ", corePoolSize="
				+ corePoolSize + ", maximumPoolSize=" + maximumPoolSize + ", keepAliveTime=" + keepAliveTime
				+ ", taskQueueSize=" + taskQueueSize + ", scanPackage=" + Arrays.toString(scanPackage)
				+ ", idleCheckEnabled=" + idleCheckEnabled + ", readerIdleTime=" + readerIdleTime + ", writerIdleTime="
				+ writerIdleTime + ", allIdleTime=" + allIdleTime + ", serverType=" + serverType + ", serializerType="
				+ serializerType + ", websocketPath=" + websocketPath + ", httpMaxContentLength=" + httpMaxContentLength
				+ ", useSSL=" + useSSL + ", componentObjectMapper=" + componentObjectMapper + ", messageMethodInvoker="
				+ messageMethodInvoker + ", eventMethodInvoker=" + eventMethodInvoker + "]";
	}

	public SocketComponentObjectMapper getComponentObjectMapper() {
		return componentObjectMapper;
	}
	public void setComponentObjectMapper(SocketComponentObjectMapper componentObjectMapper) {
		this.componentObjectMapper = componentObjectMapper;
	}
	public MessageMethodInvoker getMessageMethodInvokeMapper() {
		return messageMethodInvoker;
	}
	public void setMessageMethodInvokeMapper(MessageMethodInvoker messageMethodInvoker) {
		this.messageMethodInvoker = messageMethodInvoker;
	}
	public EventMethodInvoker getEventMethodInvokeMapper() {
		return eventMethodInvoker;
	}
	public void setEventMethodInvokeMapper(EventMethodInvoker eventMethodInvoker) {
		this.eventMethodInvoker = eventMethodInvoker;
	}
	public boolean isUseSSL() {
		return useSSL;
	}
	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}
	
	
	
	
	
	

}
