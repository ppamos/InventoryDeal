package com.amos.inventory.core;

public interface RedisHashCommand
{
    String hGet(String key, String filed);


    Long hIncrBy(String key, String fileName, long number);
}
