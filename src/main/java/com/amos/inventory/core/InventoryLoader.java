package com.amos.inventory.core;

/**
 * @author amos
 * @since 2021/8/21 14:34
 */
@FunctionalInterface
public interface InventoryLoader {
    InventoryLoaderResult loadInventory(String inventoryCode, int quantity);
}
