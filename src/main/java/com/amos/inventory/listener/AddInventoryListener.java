package com.amos.inventory.listener;

import com.amos.inventory.result.AddInventoryResult;

/**
 * @author amos
 * @since 2021/8/21 13:44
 */
public interface AddInventoryListener extends InventoryListener {

   void beforeAddInventory(Long inventoryId);

   void onErrorDo(Long inventoryId, Exception e);

   void afterAddInventory(Long inventoryId, AddInventoryResult addInventoryResult);
}
