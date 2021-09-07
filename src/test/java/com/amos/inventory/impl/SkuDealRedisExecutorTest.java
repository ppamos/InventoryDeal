package com.amos.inventory.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.amos.inventory.constant.SkuDealConstant;

public class SkuDealRedisExecutorTest {

	@Test
	public void executorAddInventory() {
		DefaultRedisTemplate defaultRedisTemplate = new DefaultRedisTemplate(new TestJedisFactory());
		SkuDealRedisExecutor skuDealRedisExecutor = new SkuDealRedisExecutor(defaultRedisTemplate);
		skuDealRedisExecutor.executorAddInventory("cis_inventory_1", 100);
		skuDealRedisExecutor.executorAddInventory("cis_inventory_2", 100);
		skuDealRedisExecutor.executorAddInventory("cis_inventory_3", 100);
	}

	@Test
	public void executorFreezeInventory() {
		DefaultRedisTemplate defaultRedisTemplate = new DefaultRedisTemplate(new TestJedisFactory());
		SkuDealRedisExecutor skuDealRedisExecutor = new SkuDealRedisExecutor(defaultRedisTemplate);
		Map<String, String> parameters = new HashMap<>();
		parameters.put(SkuDealConstant.CHECK_FREEZE, "cis_check_freeze_name");
		parameters.put(SkuDealConstant.DELAY_TIME, "100");
		skuDealRedisExecutor.executorFreezeInventory("cis_4567890",Arrays.asList("cis_inventory_1","cis_inventory_2","cis_inventory_3"), Arrays.asList(1, 1, 1), parameters);
	}

	@Test
	public void transformLuaTableString()
	{
		List<String> strings = Arrays.asList("cis_inventory_1","cis_inventory_2","cis_inventory_3");
		DefaultRedisTemplate defaultRedisTemplate = new DefaultRedisTemplate(new TestJedisFactory());
		SkuDealRedisExecutor skuDealRedisExecutor = new SkuDealRedisExecutor(defaultRedisTemplate);
		System.out.println(skuDealRedisExecutor.transformLuaTableString(strings));
	}
}