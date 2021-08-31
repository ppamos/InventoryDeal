package com.amos.inventory.core;

import redis.clients.jedis.Jedis;

public interface JedisFactory
{
    Jedis getJedis();
}
