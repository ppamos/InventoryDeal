package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.constant.skuDeal.SkuDealReleaseInventoryResultStatus;
import com.amos.inventory.result.ReleaseInventoryResult;

public class SkuDealReleaseInventoryResult implements ReleaseInventoryResult
{
    private SkuDealReleaseInventoryResultStatus skuDealReleaseInventoryResultStatus;

    public SkuDealReleaseInventoryResultStatus getSkuDealReleaseInventoryResultStatus()
    {
        return skuDealReleaseInventoryResultStatus;
    }

    public void setSkuDealReleaseInventoryResultStatus(
            SkuDealReleaseInventoryResultStatus skuDealReleaseInventoryResultStatus)
    {
        this.skuDealReleaseInventoryResultStatus = skuDealReleaseInventoryResultStatus;
    }
}
