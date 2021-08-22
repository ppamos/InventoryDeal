package com.amos.inventory.impl;

/**
 * @author amos
 * @since 2021/8/22 16:44
 */
public interface InventoryDealKeyCreator {

    String getInventoryKey(Long inventoryId);

    String getVersionKey(String version);

    String getCheckFreezeName();
}
