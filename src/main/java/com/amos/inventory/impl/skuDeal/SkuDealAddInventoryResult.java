package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.constant.skuDeal.SkuDealAddInventoryResultStatus;
import com.amos.inventory.result.AddInventoryResult;

public class SkuDealAddInventoryResult implements AddInventoryResult
{
    private SkuDealAddInventoryResultStatus skuDealAddInventoryResultStatus;

    public SkuDealAddInventoryResultStatus getSkuDealAddInventoryResultStatus()
    {
        return skuDealAddInventoryResultStatus;
    }

    public void setSkuDealAddInventoryResultStatus(
            SkuDealAddInventoryResultStatus skuDealAddInventoryResultStatus)
    {
        this.skuDealAddInventoryResultStatus = skuDealAddInventoryResultStatus;
    }
}
