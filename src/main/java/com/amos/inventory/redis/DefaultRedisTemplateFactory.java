package com.amos.inventory.redis;

public class DefaultRedisTemplateFactory implements RedisTemplateFactory
{
    private JedisFactory jedisFactory;

    public DefaultRedisTemplateFactory(JedisFactory jedisFactory)
    {
        this.jedisFactory = jedisFactory;
    }

    @Override public RedisTemplate getRedisTemplate()
    {
        return new DefaultRedisTemplate(jedisFactory.getJedis());
    }
}
