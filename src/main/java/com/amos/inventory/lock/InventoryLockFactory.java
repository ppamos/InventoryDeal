package com.amos.inventory.lock;

public interface InventoryLockFactory {
    InventoryLock getLock(String key);
}
