package com.xzcode.socket.core.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SocketJsonUtil {
	
	private static final Gson gson = new GsonBuilder().serializeNulls().create();
	
	public static Gson gson() {
		return gson;
	}

}
