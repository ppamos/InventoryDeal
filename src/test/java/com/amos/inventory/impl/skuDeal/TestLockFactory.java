package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.lock.InventoryLock;
import com.amos.inventory.lock.InventoryLockFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TestLockFactory implements InventoryLockFactory
{

    @Override public InventoryLock getLock(String key)
    {

        return new TestLock();
    }
}
