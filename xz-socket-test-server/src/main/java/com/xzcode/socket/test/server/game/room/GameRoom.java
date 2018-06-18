package com.xzcode.socket.test.server.game.room;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 游戏房间基类
 * 
 * 
 * @author zai
 * 2018-05-24
 */
public class GameRoom {
	
	private int maxRounds;
	
	private int currentRound;
	
	private ScheduledExecutorService  timerService;
	
	public void startSecondsCountdownTask(Runnable task) {
		ScheduledFuture<?> schedule = timerService.scheduleWithFixedDelay(task, 1, 1 , TimeUnit.SECONDS);
	}

}
