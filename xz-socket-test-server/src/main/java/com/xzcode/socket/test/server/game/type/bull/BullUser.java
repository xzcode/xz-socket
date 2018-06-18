package com.xzcode.socket.test.server.game.type.bull;

import com.xzcode.socket.test.server.game.model.poker.Card;
import com.xzcode.socket.test.server.game.model.user.GameUser;

/**
 * 牛牛玩家
 * 
 * 
 * @author zai
 * 2018-05-24
 */
public class BullUser extends GameUser {
	protected Card[] cards = new Card[5];
}
