package com.amos.inventory.core;

import java.util.List;

/**
 * @author amos
 * @since 2021/8/22 11:27
 * @desc redis操作类。可用适配器模式包装spring redisTemplate
 */
public interface RedisTemplate {
   <T> T executeLua(String lua, Class<T> returnType, List<Object> keys, Object... args);
}
