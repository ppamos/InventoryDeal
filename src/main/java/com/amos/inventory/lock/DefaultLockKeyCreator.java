package com.amos.inventory.lock;

/**
 * @author amos
 * @since 2021/8/22 10:54
 */
public class DefaultLockKeyCreator implements InventoryLockKeyCreator{
    @Override
    public String getGlobalKey(String version) {
        return null;
    }

    @Override
    public String getAddInventoryKey(Long inventoryId) {
        return null;
    }
}
