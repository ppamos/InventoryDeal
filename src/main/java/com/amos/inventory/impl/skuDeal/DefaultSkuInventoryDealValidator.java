package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.core.InventoryDealKeyCreator;
import com.amos.inventory.core.InventoryDealValidator;
import com.amos.inventory.lock.InventoryLockFactory;
import com.amos.inventory.lock.InventoryLockKeyCreator;
import com.amos.inventory.redis.InventoryRedisExecutor;
import com.amos.inventory.redis.RedisTemplate;
import com.amos.inventory.util.Assert;

public class DefaultSkuInventoryDealValidator implements InventoryDealValidator<InventorySkuRedisDeal> {
	@Override
	public void verify(InventorySkuRedisDeal inventorySkuRedisDeal) {
		RedisTemplate redisTemplate = inventorySkuRedisDeal.getRedisTemplate();
		Assert.noNull(redisTemplate, "redisTemplate is null");
		InventoryDealKeyCreator inventoryDealKeyCreator = inventorySkuRedisDeal.getInventoryDealKeyCreator();
		Assert.noNull(inventoryDealKeyCreator, "inventoryDealKeyCreator is null");
		int oneTimeMaxFreezeSkuNum = inventorySkuRedisDeal.getOneTimeMaxFreezeSkuNum();
		Assert.isTrue(oneTimeMaxFreezeSkuNum > 0, "oneTimeMaxFreezeSkuNum need greater than zero");
		InventoryRedisExecutor inventoryRedisExecutor = inventorySkuRedisDeal.getInventoryRedisExecutor();
		Assert.noNull(inventoryRedisExecutor, "inventoryRedisExecutor is null");
		InventoryLockKeyCreator keyCreator = inventorySkuRedisDeal.getKeyCreator();
		Assert.noNull(keyCreator, "LockKeyCreator is null");
		InventoryLockFactory inventoryLockFactory = inventorySkuRedisDeal.getInventoryLockFactory();
		Assert.noNull(inventoryLockFactory, "inventoryLockFactory is null");
	}
}
