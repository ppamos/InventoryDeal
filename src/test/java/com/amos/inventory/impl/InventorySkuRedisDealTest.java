package com.amos.inventory.impl;

import com.amos.inventory.exception.InventoryLockException;
import com.amos.inventory.impl.skuDeal.*;
import com.amos.inventory.redis.RedisTemplate;
import org.junit.Before;
import org.junit.Test;

import com.amos.inventory.redis.DefaultRedisTemplateFactory;

public class InventorySkuRedisDealTest {
	private InventorySkuRedisDeal inventorySkuRedisDeal;

	private SkuDealInventoryRedisQuery skuDealInventoryRedisQuery;

	@Before
	public void buildSkuDeal() {
        RedisTemplate redisTemplate = new DefaultRedisTemplateFactory(new TestJedisFactory()).getRedisTemplate();
        DefaultSkuInventoryDealKeyCreator defaultSkuInventoryDealKeyCreator = new DefaultSkuInventoryDealKeyCreator(
                new DefaultProjectDefinition("c_wh", "cis"));
        this.inventorySkuRedisDeal = InventorySkuRedisDealBuilder.anInventorySkuRedisDeal().withRedisTemplate(redisTemplate)
				.withInventoryLockFactory(new TestLockFactory()).withInventoryDealKeyCreator(defaultSkuInventoryDealKeyCreator).build();
		this.skuDealInventoryRedisQuery =new SkuDealInventoryRedisQuery(defaultSkuInventoryDealKeyCreator,redisTemplate);
	}

	@Test
	public void doReleaseInventory() {
	}

	@Test
	public void doConsumerInventory() {
	}

	@Test
	public void doFreezeInventory() {
	}

	@Test
	public void doAddInventory() throws InventoryLockException {
	    String versionCode="12345678";
	    int loadQuantity=100;
        int stockAmount = skuDealInventoryRedisQuery.getStockAmount(versionCode);
        inventorySkuRedisDeal.addInventory(versionCode, (n) -> {
			TestInventoryLoaderResult testInventoryLoaderResult = new TestInventoryLoaderResult();
			testInventoryLoaderResult.setInventoryCode(n);
			testInventoryLoaderResult.setLoadQuantity(loadQuantity);
			return testInventoryLoaderResult;
		});
        int newStockAmount = skuDealInventoryRedisQuery.getStockAmount(versionCode);
        assert stockAmount+loadQuantity==newStockAmount;
    }

	@Test
	public void doAckConsumer() {
	}
}