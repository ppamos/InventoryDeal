package com.amos.inventory.lock;

public interface InventoryLockKeyCreator {
    String getGlobalKey(String version);

    String getAddInventoryKey(Long inventoryId);
}
