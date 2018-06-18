package com.xzcode.socket.test.server.game;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.xzcode.socket.test.server.game.room.GameRoom;

/**
 * 游戏中心
 * 
 * 
 * @author zai
 * 2018-05-23
 */
public class GameCenter {
	
	/**
	 * 定时任务执行服务
	 */
	 private ScheduledExecutorService timerService = Executors.newScheduledThreadPool(10);  
	 
	 
	/**
	 * 最大游戏房间数
	 */
	private int maxRooms = 1000;
	
	private ConcurrentHashMap<String, GameRoom> gameRooms = new ConcurrentHashMap<>();

	
	
	
	
	
}
