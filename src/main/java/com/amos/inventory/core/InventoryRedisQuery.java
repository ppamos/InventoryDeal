package com.amos.inventory.core;

public interface InventoryRedisQuery
{
    public int getStockAmount(String inventoryCode);

    public int getBookAmount(String inventoryCode);

    public VersionStock queryVersionStock(String version);
}
