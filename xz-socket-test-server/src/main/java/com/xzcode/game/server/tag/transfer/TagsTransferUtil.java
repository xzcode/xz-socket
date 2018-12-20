package com.xzcode.game.server.tag.transfer;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xzcode.game.server.tag.SocketTags;

public class TagsTransferUtil {
	
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) throws Exception {
		
		Map<String, Object> tagMap = new HashMap<>();
		
		Class<?>[] classes = SocketTags.class.getClasses();
		
		for (Class<?> class1 : classes) {
			Map<String, Integer> subTagMap = new HashMap<>();
			tagMap.put(class1.getSimpleName(), subTagMap);
			Field[] fields = class1.getDeclaredFields();
			for (Field field : fields) {
				System.out.println(field.getInt(class1));
				subTagMap.put(field.getName(), field.getInt(class1));
			}
		}
		
		String json = gson.toJson(tagMap);
		json = "var socketTags = " + json;
		System.out.println(json);
		String outpath = TagsTransferUtil.class.getResource("./").toString().replace("/bin/main/", "/src/main/java/").replace("file:/C:", "")+ "socket_tags.js";
		System.out.println(outpath);
		File file = new File(outpath);
		
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(json.getBytes());
		fileOutputStream.close();
		
		System.out.println("done!");

	}

}
