package com.zrahh.SpiderDemo.repository;

import com.zrahh.SpiderDemo.utils.RedisUtils;

import redis.clients.jedis.Jedis;
/*
 * 声明一个基于redis公共仓库的实现类
 */

public class RedisRepositoryImpl implements Repository {
	// 定义一个List key
	public String urlList = "urlList";
	@Override
	public void add(String url) {
		// 从redis连接池中get一个redis连接。
		Jedis jedis = RedisUtils.getJedis();
		jedis.lpush(urlList, url);
		jedis.close();
	}

	@Override
	public String poll() {
		Jedis jedis = RedisUtils.getJedis();
		String rpop = jedis.rpop(urlList);
		// 先将连接池关闭然后返回数据，不关的话会导致连接不够用，报错
		jedis.close();
		return rpop;
	}

}
