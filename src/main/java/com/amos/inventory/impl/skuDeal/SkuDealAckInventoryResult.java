package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.constant.skuDeal.SkuDealAckInventoryResultStatus;
import com.amos.inventory.result.AckConsumerResult;

public class SkuDealAckInventoryResult implements AckConsumerResult
{
	private SkuDealAckInventoryResultStatus skuDealAckInventoryResultStatus;

	public SkuDealAckInventoryResultStatus getSkuDealAckInventoryResultStatus() {
		return skuDealAckInventoryResultStatus;
	}

	public void setSkuDealAckInventoryResultStatus(SkuDealAckInventoryResultStatus skuDealAckInventoryResultStatus) {
		this.skuDealAckInventoryResultStatus = skuDealAckInventoryResultStatus;
	}
}
