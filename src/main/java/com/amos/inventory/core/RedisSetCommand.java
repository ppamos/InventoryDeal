package com.amos.inventory.core;

import java.util.Set;

public interface RedisSetCommand
{
    Long sAdd(String key, String... value);

    Set<String> sMembers(String key);

    Boolean sMove(String source, String destination , String member);
}
