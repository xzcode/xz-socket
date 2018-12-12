package xz.commons.socket.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xz.commons.socket.core.session.SocketSessionUtil;
import xz.commons.socket.core.session.imp.SocketSession;

public class SocketEventTask implements Runnable{
	
private final static Logger LOGGER = LoggerFactory.getLogger(SocketEventTask.class);
	
	private EventMethodInvoker eventMethodInvoker;
	
	public void setMethodInvokeMapper(EventMethodInvoker eventMethodInvoker) {
		this.eventMethodInvoker = eventMethodInvoker;
	}

	/**
	 * session对象
	 */
	private SocketSession session;
	
	/**
	 * event标识
	 */
	private String socketEvent;
	
	

	public SocketEventTask(SocketSession session, String socketEvent, EventMethodInvoker eventMethodInvoker) {
		super();
		this.session = session;
		this.socketEvent = socketEvent;
		this.eventMethodInvoker = eventMethodInvoker;
	}

	@Override
	public void run() {
		
		SocketSessionUtil.setSession(this.session);
		try {
			
			LOGGER.debug("Runing  SocketEventTask... tag:{}", socketEvent);
			
			eventMethodInvoker.invoke(socketEvent);
			
		} catch (Exception e) {
			LOGGER.error("Socket Event Task Error!!", e);
		}
		SocketSessionUtil.removeSession();
	}

}
