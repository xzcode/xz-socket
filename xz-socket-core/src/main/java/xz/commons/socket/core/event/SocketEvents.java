package xz.commons.socket.core.event;

/**
 * socket 事件标识
 * 
 * 
 * @author zai
 * 2017-08-03
 */
public interface SocketEvents {
	
	/**
	 * 空闲状态 事件
	 * 
	 * 
	 * @author zai
	 * 2017-08-03
	 */
	interface IdleState {
		
		/**
	     * 读空闲
	     */
	    int READER_IDLE = 1001;
	    /**
	     * 写空闲
	     */
	    int WRITER_IDLE = 1002;
	    /**
	     * 读与写空闲
	     */
	    int ALL_IDLE =1003;

	}
	
	
	/**
	 * channel状态
	 * 
	 * 
	 * @author zai
	 * 2017-09-25
	 */
	interface ChannelState {
		
		/**
	     * channel 激活
	     */
	    int ACTIVE = 2001;
	    /**
	     * channel 无效化
	     */
	    int INACTIVE = 2002;

	}

}
