package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.exception.InventoryLockException;
import com.amos.inventory.lock.InventoryLock;

public class TestLock implements InventoryLock
{
    @Override public void lock() throws InventoryLockException
    {

    }

    @Override public void releaseLock() throws InventoryLockException
    {

    }

    @Override public String getKey()
    {
        return null;
    }
}
