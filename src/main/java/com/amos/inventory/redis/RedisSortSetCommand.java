package com.amos.inventory.redis;

import java.util.Set;

public interface RedisSortSetCommand
{
    Set<String> zRangeByScore(String key, double min, double max);

    Long zRem(String key, String... members);
}
