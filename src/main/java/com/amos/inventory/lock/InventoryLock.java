package com.amos.inventory.lock;

import com.amos.inventory.exception.InventoryLockException;

public interface InventoryLock {
    void Lock() throws InventoryLockException;

    void releaseLock() throws InventoryLockException;

    String getKey();
}
