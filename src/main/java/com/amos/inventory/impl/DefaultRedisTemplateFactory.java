package com.amos.inventory.impl;

import com.amos.inventory.core.JedisFactory;
import com.amos.inventory.core.RedisTemplate;
import com.amos.inventory.core.RedisTemplateFactory;

public class DefaultRedisTemplateFactory implements RedisTemplateFactory
{
    private JedisFactory jedisFactory;

    public DefaultRedisTemplateFactory(JedisFactory jedisFactory)
    {
        this.jedisFactory = jedisFactory;
    }

    @Override public RedisTemplate getRedisTemplate()
    {
        return new DefaultRedisTemplate(jedisFactory);
    }
}
