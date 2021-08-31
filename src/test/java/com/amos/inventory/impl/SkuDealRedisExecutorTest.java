package com.amos.inventory.impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class SkuDealRedisExecutorTest
{

    @Test
    public void executorAddInventory()
    {
        DefaultRedisTemplate defaultRedisTemplate = new DefaultRedisTemplate(new TestJedisFactory());
        SkuDealRedisExecutor skuDealRedisExecutor = new SkuDealRedisExecutor(defaultRedisTemplate);
        skuDealRedisExecutor.executorAddInventory("test123",100);
    }
}