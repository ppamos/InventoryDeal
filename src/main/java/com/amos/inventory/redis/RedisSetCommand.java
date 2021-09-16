package com.amos.inventory.redis;

import java.util.Set;

public interface RedisSetCommand
{
    Long sAdd(String key, String... members);

    Set<String> sMembers(String key);

    Boolean sMove(String source, String destination , String member);
}
