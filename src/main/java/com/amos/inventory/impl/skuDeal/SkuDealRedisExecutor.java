package com.amos.inventory.impl.skuDeal;

import static com.amos.inventory.constant.skuDeal.SkuDealConstant.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amos.inventory.constant.skuDeal.*;
import com.amos.inventory.exception.InventoryException;
import com.amos.inventory.redis.InventoryRedisExecutor;
import com.amos.inventory.redis.RedisTemplate;
import com.amos.inventory.result.*;
import com.amos.inventory.util.Assert;
import com.amos.inventory.util.LuaUtils;

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

	private final static String ACK_LUA;

	public SkuDealRedisExecutor(RedisTemplate redisTemplate) {
		Assert.noNull(redisTemplate, "redisTemplate is null");
		this.redisTemplate = redisTemplate;
	}

	static {
		FREEZE_LUA = loadLua("/lua/skuDeal/freeze.lua");
		RELEASE_LUA = loadLua("/lua/skuDeal/release.lua");
		CONSUMER_LUA = loadLua("/lua/skuDeal/consumer.lua");
		ADD_LUA = loadLua("/lua/skuDeal/add.lua");
		ACK_LUA = loadLua("/lua/skuDeal/ack.lua");
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
		Assert.notEmptyMap(parameters, "parameters is null");
		String checkFreezeName = parameters.get(SkuDealConstant.CHECK_FREEZE);
		String delayTime = parameters.get(SkuDealConstant.DELAY_TIME);
		Assert.notEmptyString(checkFreezeName, "checkFreezeName is null");
		Assert.notEmptyString(delayTime, "delayTime is null");
		List result = redisTemplate.executeLua(FREEZE_LUA, List.class, Arrays.asList(versionKey, checkFreezeName, delayTime, LuaUtils.transformLuaTableString(inventoryKeyList, (t) -> t.toString()),
				STOCK_AMOUNT_NAME, BOOK_AMOUNT_NAME, INVENTORY_KEY_LIST_NAME, CHECK_FREEZE_TIMES_NAME, STATUS_NAME, WAIT_ACK_TIMES_NAME, AMOUNT_NAME_SUFFIX));
		Assert.notEmptyCollection(result, "result is null");
		Long resultCode = (Long) result.get(0);
		SkuDealFreezeInventoryResultStatus resultEnum = SkuDealFreezeInventoryResultStatus.getByCode(resultCode.byteValue());
		SkuDealFreezeInventoryResult skuDealFreezeInventoryResult = new SkuDealFreezeInventoryResult();
		skuDealFreezeInventoryResult.setSkuDealFreezeInventoryResultStatus(resultEnum);
		if (SkuDealFreezeInventoryResultStatus.NOT_ENOUGH_STOCK.equals(resultEnum)) {
			Long dealInventoryLine = (Long) result.get(1);
			skuDealFreezeInventoryResult.setCurrentDealLine(dealInventoryLine.intValue());
		}
		return skuDealFreezeInventoryResult;
	}

	@Override
	public AddInventoryResult executorAddInventory(String inventoryKey, int quantity) {
		Assert.notEmptyString(inventoryKey, "executorAddInventory inventoryKey is null");
		Assert.isTrue(quantity > 0, "executorAddInventory quantity is less than zero");
		Long result = redisTemplate.executeLua(ADD_LUA, Long.class, Arrays.asList(inventoryKey, BOOK_AMOUNT_NAME, STOCK_AMOUNT_NAME), quantity);
		Assert.noNull(result, "result is null");
		SkuDealAddInventoryResult skuDealAddInventoryResult = new SkuDealAddInventoryResult();
		skuDealAddInventoryResult.setSkuDealAddInventoryResultStatus(SkuDealAddInventoryResultStatus.getByCode(result.byteValue()));
		return skuDealAddInventoryResult;
	}

	@Override
	public ConsumerInventoryResult executorConsumerInventory(String versionKey, Map<String, String> parameters) {
		Assert.notEmptyString(versionKey, "executorConsumerInventory inventoryKey is null");
		Assert.notEmptyMap(parameters, "parameters is null");
		String checkFreezeName = parameters.get(SkuDealConstant.CHECK_FREEZE);
		String waitAckName = parameters.get(SkuDealConstant.WAIT_ACK);
		String deadCheckFreezeName = parameters.get(SkuDealConstant.DEAD_FREEZE);
		Assert.notEmptyString(checkFreezeName, "checkFreezeName is null");
		Assert.notEmptyString(waitAckName, "waitAckName is null");
		Assert.notEmptyString(deadCheckFreezeName, "deadCheckFreezeName is null");
		Long result = redisTemplate.executeLua(CONSUMER_LUA, Long.class,
				Arrays.asList(versionKey, waitAckName, checkFreezeName, deadCheckFreezeName, STATUS_NAME, BOOK_AMOUNT_NAME, INVENTORY_KEY_LIST_NAME, AMOUNT_NAME_SUFFIX));
		Assert.noNull(result, "result is null");
		SkuDealConsumerInventoryResult skuDealConsumerInventoryResult = new SkuDealConsumerInventoryResult();
		skuDealConsumerInventoryResult.setSkuDealConsumerInventoryResultStatus(SkuDealConsumerInventoryResultStatus.getByCode(result.byteValue()));
		return skuDealConsumerInventoryResult;
	}

	@Override
	public ReleaseInventoryResult executorReleaseInventory(String versionKey, Map<String, String> parameters) {
		Assert.notEmptyString(versionKey, "executorReleaseInventory inventoryKey is null");
		Assert.notEmptyMap(parameters, "parameters is null");
		String checkFreezeName = parameters.get(SkuDealConstant.CHECK_FREEZE);
		String deadCheckFreezeName = parameters.get(SkuDealConstant.DEAD_FREEZE);
		Assert.notEmptyString(checkFreezeName, "checkFreezeName is null");
		Assert.notEmptyString(deadCheckFreezeName, "deadCheckFreezeName is null");
		Long result = redisTemplate.executeLua(RELEASE_LUA, Long.class,
				Arrays.asList(versionKey, checkFreezeName, deadCheckFreezeName, STATUS_NAME, STOCK_AMOUNT_NAME, INVENTORY_KEY_LIST_NAME, AMOUNT_NAME_SUFFIX));
		Assert.noNull(result, "result is null");
		SkuDealReleaseInventoryResult skuDealReleaseInventoryResult = new SkuDealReleaseInventoryResult();
		skuDealReleaseInventoryResult.setSkuDealReleaseInventoryResultStatus(SkuDealReleaseInventoryResultStatus.getByCode(result.byteValue()));
		return skuDealReleaseInventoryResult;
	}

	@Override
	public AckConsumerResult executorAckInventory(String versionKey, Map<String, String> parameters) {
		Assert.notEmptyString(versionKey, "executorConsumerInventory inventoryKey is null");
		Assert.notEmptyMap(parameters, "parameters is null");
		String waitAckName = parameters.get(SkuDealConstant.WAIT_ACK);
		String deadAckName = parameters.get(SkuDealConstant.DEAD_ACK);
		Assert.notEmptyString(waitAckName, "waitAckName is null");
		Assert.notEmptyString(deadAckName, "deadAckName is null");
		Long result = redisTemplate.executeLua(ACK_LUA, Long.class, Arrays.asList(versionKey, waitAckName, deadAckName, STATUS_NAME));
		Assert.noNull(result, "result is null");
		SkuDealAckInventoryResult skuDealAckInventoryResult = new SkuDealAckInventoryResult();
		skuDealAckInventoryResult.setSkuDealAckInventoryResultStatus(SkuDealAckInventoryResultStatus.getByCode(result.byteValue()));
		return skuDealAckInventoryResult;
	}

}
