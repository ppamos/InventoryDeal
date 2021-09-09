package com.amos.inventory.core;

public interface InventoryQuery
{
    public int queryStockAmount(String inventoryCode);


    public int queryBookAmount(String inventoryCode);
}
