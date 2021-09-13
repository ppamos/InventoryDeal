package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.impl.DefaultProjectDefinition;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultSkuInventoryDealKeyCreatorTest
{
    private DefaultSkuInventoryDealKeyCreator defaultSkuInventoryDealKeyCreator;

    @Before
    public void buildKeyCreator()
    {
       this.defaultSkuInventoryDealKeyCreator = new DefaultSkuInventoryDealKeyCreator(
                new DefaultProjectDefinition("v_wh", "cis"));
    }



    @Test
    public void getVersionByVersionKey()
    {
        String version="1110";
        String versionKey = defaultSkuInventoryDealKeyCreator.getVersionKey(version);
        String versionByVersionKey = defaultSkuInventoryDealKeyCreator.getVersionByVersionKey(versionKey);
        assert version.equals(versionByVersionKey);
    }

    @Test
    public void getInventoryCodeByInventoryKey()
    {
        String inventoryCode="100";
        String inventoryKey = defaultSkuInventoryDealKeyCreator.getInventoryKey(inventoryCode);
        String inventoryCodeByInventoryKey = defaultSkuInventoryDealKeyCreator.getInventoryCodeByInventoryKey(inventoryKey);
        assert inventoryCode.equals(inventoryCodeByInventoryKey);
    }
}