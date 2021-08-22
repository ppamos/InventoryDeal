package com.amos.inventory.core;

/**
 * @author amos
 * @since 2021/8/22 11:05
 */
public interface InventoryRedisDeal {
    RedisTemplateFactory getRedisTemplateFactory();

    InventoryRedisExecutor getInventoryRedisExecutor();
}
