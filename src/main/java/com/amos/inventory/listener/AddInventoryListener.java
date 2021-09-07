package com.amos.inventory.listener;

import com.amos.inventory.result.AddInventoryResult;

/**
 * @author amos
 * @since 2021/8/21 13:44
 */
public interface AddInventoryListener extends InventoryListener {

   void beforeAddInventory(String inventoryCode);

   void onErrorDo(String inventoryCode, Exception e);

   void afterAddInventory(String inventoryCode, AddInventoryResult addInventoryResult);
}
