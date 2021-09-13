package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.constant.skuDeal.SkuDealFreezeInventoryResultStatus;
import com.amos.inventory.result.FreezeInventoryResult;

public class SkuDealFreezeInventoryResult implements FreezeInventoryResult {
	private SkuDealFreezeInventoryResultStatus skuDealFreezeInventoryResultStatus;

	private Integer currentDealLine;
    public SkuDealFreezeInventoryResultStatus getSkuDealFreezeInventoryResultStatus()
    {
        return skuDealFreezeInventoryResultStatus;
    }

    public void setSkuDealFreezeInventoryResultStatus(
            SkuDealFreezeInventoryResultStatus skuDealFreezeInventoryResultStatus)
    {
        this.skuDealFreezeInventoryResultStatus = skuDealFreezeInventoryResultStatus;
    }

    public Integer getCurrentDealLine()
    {
        return currentDealLine;
    }

    public void setCurrentDealLine(Integer currentDealLine)
    {
        this.currentDealLine = currentDealLine;
    }
}
