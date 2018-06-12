package xz.commons.socket.core.starter;

import xz.commons.socket.core.config.SocketServerConfig;

public interface SocketServerStarter {
	
	SocketServerStarter run();
	
	SocketServerStarter shutdown();
	
	void setConfig(SocketServerConfig config);
}
