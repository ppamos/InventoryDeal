package com.amos.inventory.impl.skuDeal;

import static com.amos.inventory.constant.skuDeal.SkuDealConstant.*;

import java.util.HashMap;
import java.util.List;

import com.amos.inventory.core.InventoryDealKeyCreator;
import com.amos.inventory.core.InventoryRedisQuery;
import com.amos.inventory.core.VersionStock;
import com.amos.inventory.redis.RedisTemplate;
import com.amos.inventory.util.LuaUtils;
import com.amos.inventory.util.StringUtils;

public class SkuDealInventoryRedisQuery implements InventoryRedisQuery {
	private InventoryDealKeyCreator inventoryDealKeyCreator;

	private RedisTemplate redisTemplate;

	public SkuDealInventoryRedisQuery(InventoryDealKeyCreator inventoryDealKeyCreator, RedisTemplate redisTemplate) {
		this.inventoryDealKeyCreator = inventoryDealKeyCreator;
		this.redisTemplate = redisTemplate;
	}

	public int getStockAmount(String inventoryCode) {
		String amountString = redisTemplate.hGet(inventoryDealKeyCreator.getInventoryKey(inventoryCode), STOCK_AMOUNT_NAME);
		if (StringUtils.isNotEmpty(amountString)) {
			return Integer.parseInt(amountString);
		}
		return 0;
	}

	@Override
	public int getBookAmount(String inventoryCode) {
		String amountString = redisTemplate.hGet(inventoryDealKeyCreator.getInventoryKey(inventoryCode), BOOK_AMOUNT_NAME);
		if (StringUtils.isNotEmpty(amountString)) {
			return Integer.parseInt(amountString);
		}
		return 0;
	}

	@Override
	public VersionStock queryVersionStock(String version) {
		String value = redisTemplate.hGet(inventoryDealKeyCreator.getVersionKey(version), INVENTORY_KEY_LIST_NAME);
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		List<String> inventoryCodeList = LuaUtils.luaTableStringToList(value);
		HashMap<String, Integer> inventoryQuantityMap = new HashMap<>();
		for (String inventoryCode : inventoryCodeList) {
			String amountKey = inventoryCode + AMOUNT_NAME_SUFFIX;
			String amountString = redisTemplate.hGet(inventoryDealKeyCreator.getVersionKey(version), amountKey);
			inventoryQuantityMap.put(inventoryCode, Integer.parseInt(amountString));
		}
		VersionStock versionStock = new VersionStock();
		versionStock.setVersion(version);
		versionStock.setInventoryQuantityMap(inventoryQuantityMap);
		return versionStock;
	}
}
