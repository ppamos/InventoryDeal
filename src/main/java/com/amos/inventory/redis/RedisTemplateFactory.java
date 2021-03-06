package com.amos.inventory.redis;

/**
 * @author amos
 * @since 2021/8/22 11:06
 * @desc redisTemplate的工厂
 */
public interface RedisTemplateFactory {

    RedisTemplate getRedisTemplate();
}
