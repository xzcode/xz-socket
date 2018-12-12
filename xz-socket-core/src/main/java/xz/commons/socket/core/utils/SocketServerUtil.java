package xz.commons.socket.core.utils;

import xz.commons.socket.core.channel.SocketChannelGroups;
import xz.commons.socket.core.sender.SendModel;
import xz.commons.socket.core.session.SocketSessionUtil;
import xz.commons.socket.core.session.UserSessonMapper;
import xz.commons.socket.core.session.imp.SocketSession;

/**
 * socket服务工具
 * 
 * 
 * @author zai
 * 2017-08-04
 */
public class SocketServerUtil {
	
	/**
	 * 发送消息
	 * @param userId
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai
	 * 2017-08-04
	 */
	public static void send(Object userId, String sendTag, Object message) {
		SocketSession session = UserSessonMapper.get(userId);
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, message));
		}
	}
	
	public static void send(Object userId, String sendTag) {
		SocketSession session = UserSessonMapper.get(userId);
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, null));			
		}
	}
	
	/**
	 * 发送消息到当前通道
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai
	 * 2017-09-18
	 */
	public static void send(String sendTag, Object message) {
		SocketSession session = SocketSessionUtil.getSession();
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, message));			
		}
	}
	
	public static void send(String sendTag) {
		SocketSession session = SocketSessionUtil.getSession();
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, null));			
		}
	}
	
	/**
	 * 发送消息到所有channel
	 * @param sendTag
	 * 
	 * @author zai
	 * 2017-09-21
	 */
	public static void sendGobal(String sendTag) {
		SocketChannelGroups.getGlobalGroup().writeAndFlush(SendModel.create(sendTag, null));
	}
	
	/**
	 * 发送消息到所有channel
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai
	 * 2017-09-21
	 */
	
	public static void sendGobal(String sendTag, Object message) {
		SocketChannelGroups.getGlobalGroup().writeAndFlush(SendModel.create(sendTag, message));
	}
	
	
	/**
	 * 发送消息到所有已登录的channel
	 * @param sendTag
	 * 
	 * @author zai
	 * 2017-09-21
	 */
	public static void sendRegistered(String sendTag) {
		SocketChannelGroups.getRegisteredGroup().writeAndFlush(SendModel.create(sendTag, null));
	}
	
	/**
	 * 发送消息到所有已登录的channel
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai
	 * 2017-09-21
	 */
	
	public static void sendRegistered(String sendTag, Object message) {
		SocketChannelGroups.getRegisteredGroup().writeAndFlush(SendModel.create(sendTag, message));
	}
	
	/**
	 * 获取当前会话session对象
	 * @return
	 * 
	 * @author zai
	 * 2017-08-04
	 */
	public static SocketSession getSession() {
		return SocketSessionUtil.getSession();
	}
	
	/**
	 * 把用户绑定到当前通信会话
	 * @param userId
	 * 
	 * @author zai
	 * 2017-08-04
	 */
	public static void userRegister(Object userId) {
		SocketSession session = SocketSessionUtil.getSession();
		
		session.setRegisteredUserId(userId);
		
		//已注册会话绑定
		UserSessonMapper.put(userId, session);
		//已注册channelgroup绑定
		SocketChannelGroups.getRegisteredGroup().add(session.getChannel());
	}
	
	/**
	 * 把用户从注册绑定中移除
	 * @param userId
	 * @author zai 2017-08-19 01:09:56
	 */
	public static SocketSession userUnregister(Object userId) {
		SocketSession session = SocketSessionUtil.getSession();
		
		session.unregister();
		
		//注销会话绑定
		UserSessonMapper.remove(userId);
		
		//已注册channelgroup绑定
		SocketChannelGroups.getRegisteredGroup().remove(session.getChannel());
		
		return session;
	}
	
	/**
	 * 断开指定用户的连接
	 * @param userId
	 * @author zai 2017-08-19 01:12:07
	 */
	public static void disconnect(Object userId) {
		getSession().getChannel().close();
		SocketSession session = UserSessonMapper.get(userId);
		if (session != null && session.getChannel() != null) {
			session.getChannel().disconnect();
		}
	}
	
	/**
	 * 断开当前连接
	 * 
	 * @author zai
	 * 2017-09-21
	 */
	public static void disconnect() {
		getSession().getChannel().close();
	}

}
