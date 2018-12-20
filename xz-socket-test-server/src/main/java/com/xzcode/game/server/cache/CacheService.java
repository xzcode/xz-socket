package com.xzcode.game.server.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 分布式缓存服务类
 * <br>
 * 使用StringRedisTemplate实现，缓存对象将采用gson进行json序列化 
 * <br>
 * <br>
 * 
 * @author zai
 * 2018-06-17 21:12:14
 */
@Service
public class CacheService {
	
	private static final Gson gson = new GsonBuilder().create();
	
	
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 值操作
	 */
	private ValueOperations<String, String> opsForValue;
	
	/**
	 * list操作
	 */
	private ListOperations<String, String> opsForList;
	
	/**
	 * hash操作
	 */
	private HashOperations<String, Object, Object> opsForHash;
	
	/**
	 * set操作
	 */
	private SetOperations<String, String> opsForSet;
	
	/**
	 * zset操作
	 */
	private ZSetOperations<String, String> opsForZSet;
	
	/**
	 * 地理位置操作
	 */
	private GeoOperations<String, String> geoOperations;
	
	/**
	 * 基数统计操作
	 */
	private HyperLogLogOperations<String, String> hyperLogLogOperations;
	
	@Autowired
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.opsForValue = redisTemplate.opsForValue();
		this.opsForList = redisTemplate.opsForList();
		this.opsForHash = redisTemplate.opsForHash();
		this.opsForSet = redisTemplate.opsForSet();
		this.opsForZSet = redisTemplate.opsForZSet();
		this.geoOperations = redisTemplate.opsForGeo();
		this.hyperLogLogOperations = redisTemplate.opsForHyperLogLog();
	}
	
	/**
	 * 普通set操作
	 * @param key
	 * @param value
	 * 
	 * @author zai
	 * 2018-06-18 12:51:24
	 */
	public void setValue(String key, Object value) {
		this.opsForValue.set(key, gson.toJson(value));
	}
	
	/**
	 * 超时set操作
	 * @param key
	 * @param value
	 * @param timeout 超时时间
	 * @param unit 时间单位
	 * 
	 * @author zai
	 * 2018-06-18 12:51:36
	 */
	public void setValueTimeOut(String key, Object value, long timeout, TimeUnit unit) {
		this.opsForValue.set(key, gson.toJson(value), timeout, unit);
	}
	
	/**
	 * 获取string值
	 * @param key
	 * @return
	 * 
	 * @author zai
	 * 2018-06-18 12:52:11
	 */
	public String getStringValue(String key) {
		return this.opsForValue.get(key);
	}
	
	/**
	 * 获取整形
	 * @param key
	 * @return
	 * 
	 * @author zai
	 * 2018-06-18 12:52:19
	 */
	public Integer getIntegerValue(String key) {
		String value = this.opsForValue.get(key);
		return value == null ? null : Integer.valueOf(value);
	}
	
	/**
	 * 获取long型
	 * @param key
	 * @return
	 * 
	 * @author zai
	 * 2018-06-18 12:52:30
	 */
	public Long getLongValue(String key) {
		String value = this.opsForValue.get(key);
		return value == null ? null : Long.valueOf(value);
	}
	
	/**
	 * 获取float
	 * @param key
	 * @return
	 * 
	 * @author zai
	 * 2018-06-18 12:52:37
	 */
	public Float getFloatValue(String key) {
		String value = this.opsForValue.get(key);
		return value == null ? null : Float.valueOf(value);
	}
	
	/**
	 * 获取double值
	 * @param key
	 * @return
	 * 
	 * @author zai
	 * 2018-06-18 12:52:48
	 */
	public Double getDoubleValue(String key) {
		String value = this.opsForValue.get(key);
		return value == null ? null : Double.valueOf(value);
	}
	
	/**
	 * 获取short
	 * @param key
	 * @return
	 * 
	 * @author zai
	 * 2018-06-18 12:52:59
	 */
	public Short getShortValue(String key) {
		String value = this.opsForValue.get(key);
		return value == null ? null : Short.valueOf(value);
	}
	
	/**
	 * 获取json反序列化对象
	 * @param key
	 * @param objClass
	 * @return
	 * 
	 * @author zai
	 * 2018-06-18 12:53:13
	 */
	public <T> T getObjectValue(String key, Class<T> objClass) {
		String json = this.opsForValue.get(key);
		if (json != null) {
			return gson.fromJson(json, objClass);
		}
		return null;
	}
	
	
	

}
