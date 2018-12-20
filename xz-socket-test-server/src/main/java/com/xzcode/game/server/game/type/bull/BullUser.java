package com.xzcode.game.server.game.type.bull;

import com.xzcode.game.server.game.model.poker.Card;
import com.xzcode.game.server.game.model.user.GameUser;

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
