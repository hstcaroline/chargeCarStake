package com.sjtu.ist.charge.Util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	//private static String ADDR = "120.25.127.101";
	//private static String ADDR = "127.0.0.1";
	private static String ADDR = "139.196.40.33";
	private static int PORT = 6379;
	
	//可用连接jedis实例的最大数目，默认值为8
	private static int MAX_ACTIVE = 1024;
	//控制一个pool最多有多少个状态为空闲的jedis实例，默认值为8
	private static int MAX_IDLE = 200;
	
	//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
	private static int MAX_WAIT = 10000;
	private static int TIMEOUT = 10000;
	
	private static boolean TEST_ON_BORROW = true;
	
	private static JedisPool jedisPool = null;
	
	/**
	 * 初始化jedisPool
	 */
	static {
		//jedisPool = new JedisPool(ADDR, PORT);
		try {
			JedisPoolConfig jedisConfig = new JedisPoolConfig();
			jedisConfig.setMaxActive(MAX_ACTIVE);
			jedisConfig.setMaxIdle(MAX_IDLE);
			jedisConfig.setMaxWait(MAX_WAIT);
			jedisConfig.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(jedisConfig, ADDR, PORT, TIMEOUT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取jedis实例
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				return jedisPool.getResource();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 释放jedis实例
	 * @param jedis
	 */
	public static void removeJedis(Jedis jedis) {
		try {
			if (jedisPool != null) {
				jedisPool.returnResource(jedis);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
