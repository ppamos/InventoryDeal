package com.amos.inventory.impl;

import com.amos.inventory.constant.SkuDealConstant;
import com.amos.inventory.core.InventoryRedisExecutor;
import com.amos.inventory.core.RedisTemplate;
import com.amos.inventory.exception.InventoryException;
import com.amos.inventory.result.AddInventoryResult;
import com.amos.inventory.result.FreezeInventoryResult;
import com.amos.inventory.util.Assert;
import com.amos.inventory.util.MapUtils;
import com.amos.inventory.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author amos
 * @since 2021/8/30 22:22
 */
public class SkuDealRedisExecutor implements InventoryRedisExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(SkuDealRedisExecutor.class);

	private RedisTemplate redisTemplate;

	private final static String FREEZE_LUA;

	private final static String RELEASE_LUA;

	private final static String CONSUMER_LUA;

	private final static String ADD_LUA;

	public SkuDealRedisExecutor(RedisTemplate redisTemplate)
	{
		this.redisTemplate = redisTemplate;
	}

	static {
		FREEZE_LUA = loadLua("/lua/skuDeal/freeze.lua");
		RELEASE_LUA = loadLua("/lua/skuDeal/release.lua");
		CONSUMER_LUA = loadLua("/lua/skuDeal/consumer.lua");
		ADD_LUA = loadLua("/lua/skuDeal/add.lua");
	}

	private static String loadLua(String luaAddress) {
		InputStream resourceAsStream = SkuDealRedisExecutor.class.getResourceAsStream(luaAddress);
		try {
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = resourceAsStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result.toString("UTF-8");
		} catch (IOException e) {
			LOG.error("loadLua error ", e);
			throw new InventoryException("loadLua error");
		}
	}

	@Override
	public FreezeInventoryResult executorFreezeInventory(String versionKey, List<String> inventoryKeyList, List<Integer> inventoryNumList, Map<String, String> parameters) {
		Assert.isTrue(MapUtils.isNotEmpty(parameters), "parameters is null");
		String checkFreezeName = parameters.get(SkuDealConstant.CHECK_FREEZE);
		Assert.isTrue(StringUtils.isNotEmpty(checkFreezeName), "checkFreezeName is null");
		List result = redisTemplate.executeLua(FREEZE_LUA, List.class, Arrays.asList(checkFreezeName, versionKey), inventoryKeyList, inventoryNumList);
		return null;
	}

	@Override
	public AddInventoryResult executorAddInventory(String inventoryKey, int quantity) {
		Assert.isTrue(StringUtils.isNotEmpty(inventoryKey), "executorAddInventory inventoryKey is null");
		Assert.isTrue(quantity > 0, "executorAddInventory quantity is less than zero");
		Long result = redisTemplate.executeLua(ADD_LUA, Long.class, Arrays.asList(inventoryKey), quantity);
		return null;
	}
}
