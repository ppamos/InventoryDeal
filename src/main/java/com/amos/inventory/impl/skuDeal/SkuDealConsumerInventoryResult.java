package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.constant.skuDeal.SkuDealConsumerInventoryResultStatus;
import com.amos.inventory.result.ConsumerInventoryResult;

public class SkuDealConsumerInventoryResult implements ConsumerInventoryResult
{
    private SkuDealConsumerInventoryResultStatus skuDealConsumerInventoryResultStatus;

    public SkuDealConsumerInventoryResultStatus getSkuDealConsumerInventoryResultStatus()
    {
        return skuDealConsumerInventoryResultStatus;
    }

    public void setSkuDealConsumerInventoryResultStatus(
            SkuDealConsumerInventoryResultStatus skuDealConsumerInventoryResultStatus)
    {
        this.skuDealConsumerInventoryResultStatus = skuDealConsumerInventoryResultStatus;
    }
}
