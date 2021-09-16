package com.amos.inventory.redis;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.amos.inventory.util.Assert;
import com.amos.inventory.util.CollectionUtils;
import com.amos.inventory.util.StringUtils;

import redis.clients.jedis.Jedis;

public class DefaultRedisTemplate implements RedisTemplate {
	private Jedis jedis;

	public DefaultRedisTemplate(Jedis jedis) {
		this.jedis = jedis;
	}

	@Override
	public <T> T executeLua(String lua, Class<T> returnType, List<Object> keys, Object... args) {
		Assert.isTrue(StringUtils.isNotEmpty(lua), "lua is null");
		List<String> keyStrings = null;
		if (CollectionUtils.isNotEmpty(keys)) {
			keyStrings = keys.stream().map(n -> (String) n.toString()).collect(Collectors.toList());
		}

		List<String> argStrings = null;
		if (args != null && args.length > 0) {
			List<Object> argList = Arrays.asList(args);
			argStrings = argList.stream().map(n -> (String) n.toString()).collect(Collectors.toList());
		}
		return (T) jedis.evalsha(jedis.scriptLoad(lua), keyStrings, argStrings);
	}

	public String ping() {
		return jedis.ping();
	}

	@Override
	public String hGet(String key, String filed) {
		return jedis.hget(key, filed);
	}

	@Override
	public Long hIncrBy(String key, String fileName, long number) {
		return jedis.hincrBy(key, fileName, number);
	}

	@Override
	public Long sAdd(String key, String... members) {
		return jedis.sadd(key, members);
	}

	@Override
	public Set<String> sMembers(String key) {
		return jedis.smembers(key);
	}

	@Override
	public Boolean sMove(String source, String destination, String member) {
		return jedis.smove(source, destination, member) == 1L;
	}

	@Override
	public Set<String> zRangeByScore(String key, double min, double max) {
		return jedis.zrangeByScore(key, min, max);
	}

	@Override
	public Long zRem(String key, String... members) {
		return jedis.zrem(key, members);
	}
}
