package com.amos.inventory.redis;

public interface RedisHashCommand
{
    String hGet(String key, String filed);


    Long hIncrBy(String key, String fileName, long number);
}
