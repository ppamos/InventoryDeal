package com.amos.inventory.redis;

import redis.clients.jedis.Jedis;

public interface JedisFactory
{
    Jedis getJedis();
}
