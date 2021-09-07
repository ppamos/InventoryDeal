package com.amos.inventory.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.amos.inventory.core.JedisFactory;
import com.amos.inventory.core.RedisTemplate;
import com.amos.inventory.util.Assert;
import com.amos.inventory.util.CollectionUtils;

import com.amos.inventory.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

public class DefaultRedisTemplate implements RedisTemplate {
	private Jedis jedis;

	public DefaultRedisTemplate(JedisFactory jedisFactory) {
		this.jedis = jedisFactory.getJedis();
	}

	@Override
	public <T> T executeLua(String lua, Class<T> returnType, List<Object> keys, Object... args) {
        Assert.isTrue(StringUtils.isNotEmpty(lua),"lua is null");
		List<String> keyStrings = null;
		if (CollectionUtils.isNotEmpty(keys)) {
			keyStrings = keys.stream().map(n -> (String) n.toString()).collect(Collectors.toList());
		}

		List<String> argStrings = null;
		if (args != null && args.length > 0) {
			List<Object> argList = Arrays.asList(args);
			argStrings = argList.stream().map(n -> (String) n.toString()).collect(Collectors.toList());
		}
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			System.out.println("keys="+mapper.writeValueAsString(keys));
			System.out.println("args="+mapper.writeValueAsString(args));
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
		return (T) jedis.evalsha(jedis.scriptLoad(lua), keyStrings, argStrings);
	}

	public String ping()
    {
        return jedis.ping();
    }
}
