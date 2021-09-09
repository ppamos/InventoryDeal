package com.amos.inventory.lock;

import com.amos.inventory.exception.InventoryLockException;

public interface InventoryLock {
    void lock() throws InventoryLockException;

    void releaseLock() throws InventoryLockException;

    String getKey();
}
