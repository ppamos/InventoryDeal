package com.amos.inventory.core;

/**
 * @author amos
 * @since 2021/8/21 14:34
 */
public interface InventoryLoader {
    InventoryLoaderResult loadInventory(Long inventoryId, int quantity);

    InventoryLoaderResult loadAllInventory(Long inventoryId);
}
