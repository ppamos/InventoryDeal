package com.amos.inventory.listener;

import com.amos.inventory.result.FreezeInventoryResult;

import java.util.Map;

/**
 * @author amos
 * @since 2021/8/21 13:44
 */
public interface FreezeInventoryListener extends InventoryListener {
    void afterFreezeInventory(String version, Map<Long, Integer> inventoryNumMap, Map<String, String> parameters, FreezeInventoryResult freezeInventoryResult);

    void onErrorDo(String version, Map<Long, Integer> inventoryNumMap, Map<String, String> parameters, Exception e);

    void beforeFreezeInventory(String version, Map<Long, Integer> inventoryNumMap, Map<String, String> parameters);
}
