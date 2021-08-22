package com.amos.inventory.core;

import com.amos.inventory.result.FreezeInventoryResult;

import java.util.List;
import java.util.Map;

/**
 * @author amos
 * @since 2021/8/22 11:07
 */
public interface InventoryRedisExecutor {
    FreezeInventoryResult executorFreezeInventory(String versionKey, List<String> inventoryKeyList, List<Integer>inventoryNumList, Map<String,String> parameters);
}
