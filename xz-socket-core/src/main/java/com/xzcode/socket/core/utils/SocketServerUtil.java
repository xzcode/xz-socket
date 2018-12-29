package com.xzcode.socket.core.utils;

import com.xzcode.socket.core.channel.SocketChannelGroups;
import com.xzcode.socket.core.sender.SendModel;
import com.xzcode.socket.core.sender.callback.SocketSendMessageCallback;
import com.xzcode.socket.core.session.SocketSessionUtil;
import com.xzcode.socket.core.session.UserSessonMapper;
import com.xzcode.socket.core.session.imp.SocketSession;

/**
 * socket服务工具
 * 
 * 
 * @author zai 2017-08-04
 */
public class SocketServerUtil {

	/**
	 * 发送消息
	 * 
	 * @param userId
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai 2017-08-04
	 */
	public static void send(Object userId, String sendTag, Object message) {
		SocketSession session = UserSessonMapper.get(userId);
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, message, session));
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param userId          用户id
	 * @param sendTag         发送消息标识
	 * @param message         消息体
	 * @param successCallback 发送完成回调
	 * @author zai 2018-12-29 14:28:10
	 */
	public static void send(Object userId, String sendTag, Object message, SocketSendMessageCallback successCallback) {
		SocketSession session = UserSessonMapper.get(userId);
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, message, successCallback, session));
		}
	}

	/**
	 * 根据用户id发送消息（无消息体）
	 * 
	 * @param userId  用户id
	 * @param sendTag 发送消息标识
	 * @author zai 2018-12-29 14:25:27
	 */
	public static void send(Object userId, String sendTag) {
		SocketSession session = UserSessonMapper.get(userId);
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, null, session));
		}
	}

	/**
	 * 根据用户id发送消息（无消息体）
	 * 
	 * @param userId          用户id
	 * @param sendTag         发送消息标识
	 * @param successCallback 发送完成回调
	 * @author zai 2018-12-29 14:26:54
	 */
	public static void send(Object userId, String sendTag, SocketSendMessageCallback successCallback) {
		SocketSession session = UserSessonMapper.get(userId);
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, null, successCallback, session));
		}
	}

	/**
	 * 发送消息到当前通道
	 * 
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai 2017-09-18
	 */
	public static void send(String sendTag, Object message) {
		SocketSession session = SocketSessionUtil.getSession();
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, message, session));
		}
	}

	/**
	 * 发送消息到当前通道
	 * 
	 * @param sendTag
	 * @param message
	 * @param successCallback 发送完成回调
	 * @author zai 2018-12-29 14:24:27
	 */
	public static void send(String sendTag, Object message, SocketSendMessageCallback successCallback) {
		SocketSession session = SocketSessionUtil.getSession();
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, message, successCallback, session));
		}
	}

	/**
	 * 发送消息（无消息体）
	 * 
	 * @param sendTag
	 * @param successCallback 发送完成回调
	 * @author zai 2018-12-29 14:23:18
	 */
	public static void send(String sendTag, SocketSendMessageCallback successCallback) {
		SocketSession session = SocketSessionUtil.getSession();
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, null, successCallback, session));
		}
	}

	/**
	 * 发送消息（无消息体）
	 * 
	 * @param sendTag
	 * @author zai 2018-12-29 14:23:54
	 */
	public static void send(String sendTag) {
		SocketSession session = SocketSessionUtil.getSession();
		if (session != null) {
			session.getChannel().writeAndFlush(SendModel.create(sendTag, null, session));
		}
	}

	/**
	 * 发送消息到所有channel
	 * 
	 * @param sendTag
	 * 
	 * @author zai 2017-09-21
	 */
	public static void sendGobal(String sendTag) {
		SocketChannelGroups.getGlobalGroup()
				.writeAndFlush(SendModel.create(sendTag, null, SocketSessionUtil.getSession()));
	}

	/**
	 * 发送消息到所有channel
	 * 
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai 2017-09-21
	 */

	public static void sendGobal(String sendTag, Object message) {
		SocketChannelGroups.getGlobalGroup()
				.writeAndFlush(SendModel.create(sendTag, message, SocketSessionUtil.getSession()));
	}

	/**
	 * 发送消息到所有已登录的channel
	 * 
	 * @param sendTag
	 * 
	 * @author zai 2017-09-21
	 */
	public static void sendToAllRegistered(String sendTag) {
		SocketChannelGroups.getRegisteredGroup()
				.writeAndFlush(SendModel.create(sendTag, null, SocketSessionUtil.getSession()));
	}

	/**
	 * 发送消息到所有已登录的channel
	 * 
	 * @param sendTag
	 * @param message
	 * 
	 * @author zai 2017-09-21
	 */

	public static void sendToAllRegistered(String sendTag, Object message) {
		SocketChannelGroups.getRegisteredGroup()
				.writeAndFlush(SendModel.create(sendTag, message, SocketSessionUtil.getSession()));
	}

	/**
	 * 获取当前会话session对象
	 * 
	 * @return
	 * 
	 * @author zai 2017-08-04
	 */
	public static SocketSession getSession() {
		return SocketSessionUtil.getSession();
	}

	/**
	 * 把用户绑定到当前通信会话
	 * 
	 * @param userId
	 * 
	 * @author zai 2017-08-04
	 */
	public static void userRegister(Object userId) {
		SocketSession session = SocketSessionUtil.getSession();

		session.setRegisteredUserId(userId);

		// 已注册会话绑定
		UserSessonMapper.put(userId, session);
		// 已注册channelgroup绑定
		SocketChannelGroups.getRegisteredGroup().add(session.getChannel());
	}

	/**
	 * 判断是否已登录
	 * 
	 * @param userId
	 * @author zai 2018-12-29 10:22:11
	 */
	public static boolean isRegistered() {
		SocketSession session = SocketSessionUtil.getSession();
		return session.getRegisteredUserId() != null;
	}

	/**
	 * 把用户从注册绑定中移除
	 * 
	 * @param userId
	 * @author zai 2017-08-19 01:09:56
	 */
	public static SocketSession userUnregister(Object userId) {
		SocketSession session = SocketSessionUtil.getSession();

		session.unregister();

		// 注销会话绑定
		UserSessonMapper.remove(userId);

		// 已注册channelgroup绑定
		SocketChannelGroups.getRegisteredGroup().remove(session.getChannel());

		return session;
	}

	/**
	 * 断开指定用户的连接
	 * 
	 * @param userId
	 * @author zai 2017-08-19 01:12:07
	 */
	public static void disconnect(Object userId) {
		SocketSession session = UserSessonMapper.get(userId);
		if (session != null && session.getChannel() != null) {
			session.getChannel().close();
		}
	}

	/**
	 * 断开当前连接
	 * 
	 * @author zai 2017-09-21
	 */
	public static void disconnect() {
		getSession().getChannel().close();
	}

}
