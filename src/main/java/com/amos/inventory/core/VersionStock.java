package com.amos.inventory.core;

import java.util.Map;

public class VersionStock
{
    private String version;

    private Map<String,Integer> inventoryQuantityMap;

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public Map<String, Integer> getInventoryQuantityMap()
    {
        return inventoryQuantityMap;
    }

    public void setInventoryQuantityMap(Map<String, Integer> inventoryQuantityMap)
    {
        this.inventoryQuantityMap = inventoryQuantityMap;
    }
}
