package com.amos.inventory.core;

import java.util.List;
import java.util.Map;

import com.amos.inventory.result.*;

/**
 * @author amos
 * @since 2021/8/22 11:07
 * @desc 具体实现lua脚本的类
 */
public interface InventoryRedisExecutor
{
    FreezeInventoryResult executorFreezeInventory(String versionKey, List<String> inventoryKeyList,
            List<Integer> inventoryNumList, Map<String, String> parameters);

    AddInventoryResult executorAddInventory(String inventoryKey, int quantity);

    ConsumerInventoryResult executorConsumerInventory(String versionKey,Map<String, String> parameters);

    ReleaseInventoryResult executorReleaseInventory(String versionKey,Map<String, String> parameters);

    AckConsumerResult executorAckInventory(String versionKey,Map<String, String> parameters);
}