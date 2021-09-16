package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.core.InventoryLoaderResult;

public class TestInventoryLoaderResult implements InventoryLoaderResult
{
    private String inventoryCode;

    private int loadQuantity;

    @Override public String getInventoryCode()
    {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode)
    {
        this.inventoryCode = inventoryCode;
    }

    @Override public int getLoadQuantity()
    {
        return loadQuantity;
    }

    public void setLoadQuantity(int loadQuantity)
    {
        this.loadQuantity = loadQuantity;
    }
}
