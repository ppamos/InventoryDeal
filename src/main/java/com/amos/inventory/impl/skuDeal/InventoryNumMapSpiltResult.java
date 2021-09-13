package com.amos.inventory.impl.skuDeal;

import java.util.List;

/**
 * @author amos
 * @since 2021/8/22 15:14
 */
public class InventoryNumMapSpiltResult {
    private List<String> inventoryKeyList;

    private List<Integer> inventoryNum;

    public List<String> getInventoryKeyList() {
        return inventoryKeyList;
    }

    public void setInventoryKeyList(List<String> inventoryKeyList) {
        this.inventoryKeyList = inventoryKeyList;
    }

    public List<Integer> getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(List<Integer> inventoryNum) {
        this.inventoryNum = inventoryNum;
    }
}
