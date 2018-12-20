package com.xzcode.game.server.game.model.poker;

public class Card {
	 public static final int FLOWER_SPADE = 3;// 黑桃
	    public static final int FLOWER_HEART = 2;// 红桃
	    public static final int FLOWER_CLUB = 1;// 梅花
	    public static final int FLOWER_DIAMOND = 0;// 方片

	    public static final int NUM_A = 14;
	    public static final int NUM_K = 13;
	    public static final int NUM_Q = 12;
	    public static final int NUM_J = 11;
	    public static final int NUM_10 = 10;
	    public static final int NUM_9 = 9;
	    public static final int NUM_8 = 8;
	    public static final int NUM_7 = 7;
	    public static final int NUM_6 = 6;
	    public static final int NUM_5 = 5;
	    public static final int NUM_4 = 4;
	    public static final int NUM_3 = 3;
	    public static final int NUM_2 = 2;

	    // 单张牌大小
	    private int num;
	    // 花色
	    private int color;

	    public Card() { }

	    public Card(int color, int num) {
	        this.color = color;
	        this.num = num;
	    }

		public int getNum() {
			return num;
		}

		public Card setNum(int num) {
			this.num = num;
			return this;
		}

		public int getColor() {
			return color;
		}

		public Card setColor(int color) {
			this.color = color;
			return this;
		}

	    
}
