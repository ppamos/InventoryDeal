package com.amos.inventory.redis;

/**
 * @author amos
 * @since 2021/8/22 11:05
 */
public interface InventoryRedisDeal {
    RedisTemplate getRedisTemplate();

    InventoryRedisExecutor getInventoryRedisExecutor();
}
