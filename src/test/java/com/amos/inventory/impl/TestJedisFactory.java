package com.amos.inventory.impl;

import com.amos.inventory.core.JedisFactory;
import redis.clients.jedis.Jedis;

public class TestJedisFactory implements JedisFactory
{
    @Override public Jedis getJedis()
    {
        return new Jedis("127.0.0.1",8179);
    }
}
