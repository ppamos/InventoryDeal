package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.core.InventoryDealKeyCreator;
import com.amos.inventory.core.InventoryDealValidator;
import com.amos.inventory.core.InventoryRedisExecutor;
import com.amos.inventory.core.RedisTemplateFactory;
import com.amos.inventory.util.Assert;

public class DefaultSkuInventoryDealValidator implements InventoryDealValidator<InventorySkuRedisDeal> {
	@Override
	public void verify(InventorySkuRedisDeal inventorySkuRedisDeal) {
		RedisTemplateFactory redisTemplateFactory = inventorySkuRedisDeal.getRedisTemplateFactory();
		Assert.noNull(redisTemplateFactory, "redisTemplateFactory is null");
		InventoryDealKeyCreator inventoryDealKeyCreator = inventorySkuRedisDeal.getInventoryDealKeyCreator();
		Assert.noNull(inventoryDealKeyCreator, "inventoryDealKeyCreator is null");
		int oneTimeMaxFreezeSkuNum = inventorySkuRedisDeal.getOneTimeMaxFreezeSkuNum();
		Assert.isTrue(oneTimeMaxFreezeSkuNum > 0, "oneTimeMaxFreezeSkuNum need greater than zero");
		InventoryRedisExecutor inventoryRedisExecutor = inventorySkuRedisDeal.getInventoryRedisExecutor();
		Assert.noNull(inventoryRedisExecutor, "inventoryRedisExecutor is null");
	}
}
