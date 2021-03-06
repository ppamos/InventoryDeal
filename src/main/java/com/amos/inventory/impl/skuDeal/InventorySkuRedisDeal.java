package com.amos.inventory.impl.skuDeal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amos.inventory.constant.skuDeal.SkuDealConstant;
import com.amos.inventory.core.*;
import com.amos.inventory.redis.InventoryRedisDeal;
import com.amos.inventory.redis.InventoryRedisExecutor;
import com.amos.inventory.redis.RedisTemplate;
import com.amos.inventory.redis.RedisTemplateFactory;
import com.amos.inventory.result.*;
import com.amos.inventory.util.Assert;
import com.amos.inventory.util.MapUtils;

/**
 * @author amos
 * @since 2021/8/22 10:59
 */
public class InventorySkuRedisDeal extends AbstractInventoryDeal implements InventoryRedisDeal
{

	private InventoryRedisExecutor inventoryRedisExecutor;

	private RedisTemplate redisTemplate;

	private InventoryDealKeyCreator inventoryDealKeyCreator;

	// 一次冻结sku的个数
	private int oneTimeMaxFreezeSkuNum = 200;

	@Override
	protected ReleaseInventoryResult doReleaseInventory(String version) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put(SkuDealConstant.CHECK_FREEZE, inventoryDealKeyCreator.getCheckFreezeName());
		parameters.put(SkuDealConstant.DEAD_FREEZE, inventoryDealKeyCreator.getDeadCheckFreezeName());
		ReleaseInventoryResult releaseInventoryResult = inventoryRedisExecutor.executorReleaseInventory(inventoryDealKeyCreator.getVersionKey(version), parameters);
		return releaseInventoryResult;
	}

	@Override
	protected ConsumerInventoryResult doConsumerInventory(String version) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put(SkuDealConstant.CHECK_FREEZE, inventoryDealKeyCreator.getCheckFreezeName());
		parameters.put(SkuDealConstant.DEAD_FREEZE, inventoryDealKeyCreator.getDeadCheckFreezeName());
		parameters.put(SkuDealConstant.WAIT_ACK, inventoryDealKeyCreator.getWaitAckName());
		ConsumerInventoryResult consumerInventoryResult = inventoryRedisExecutor.executorConsumerInventory(inventoryDealKeyCreator.getVersionKey(version), parameters);
		return consumerInventoryResult;
	}

	@Override
	protected FreezeInventoryResult doFreezeInventory(String version, Map<String, Integer> inventoryNumMap, Map<String, String> parameters) {
		InventoryNumMapSpiltResult inventoryNumMapSpiltResult = this.splitInventoryNum(inventoryNumMap);
		HashMap<String, String> executorParameters = new HashMap<>();
		String checkFreezeName = inventoryDealKeyCreator.getCheckFreezeName();
		executorParameters.put(SkuDealConstant.CHECK_FREEZE, checkFreezeName);
		FreezeInventoryResult freezeInventoryResult = inventoryRedisExecutor.executorFreezeInventory(inventoryDealKeyCreator.getVersionKey(version), inventoryNumMapSpiltResult.getInventoryKeyList(),
				inventoryNumMapSpiltResult.getInventoryNum(), executorParameters);
		return freezeInventoryResult;
	}

	private InventoryNumMapSpiltResult splitInventoryNum(Map<String, Integer> inventoryNumMap) {
		Assert.isTrue(MapUtils.isNotEmpty(inventoryNumMap), "inventoryNumMap is null");
		InventoryNumMapSpiltResult inventoryNumMapSpiltResult = new InventoryNumMapSpiltResult();
		List<String> inventoryKeyList = new ArrayList<String>();
		List<Integer> numList = new ArrayList<>();
		for (String inventoryCode : inventoryNumMap.keySet()) {
			Assert.notEmptyString(inventoryCode, "inventoryCode is null");
			Integer num = inventoryNumMap.get(inventoryCode);
			Assert.isTrue(num != null && num > 0, "inventory num is null");
			numList.add(num);
			String inventoryKey = inventoryDealKeyCreator.getInventoryKey(inventoryCode);
			inventoryKeyList.add(inventoryKey);
		}
		inventoryNumMapSpiltResult.setInventoryKeyList(inventoryKeyList);
		inventoryNumMapSpiltResult.setInventoryNum(numList);
		return inventoryNumMapSpiltResult;
	}

	@Override
	protected AddInventoryResult doAddInventory(InventoryLoaderResult inventoryLoaderResult) {
		String inventoryCode = inventoryLoaderResult.getInventoryCode();
		Integer loadQuantity = inventoryLoaderResult.getLoadQuantity();
		AddInventoryResult addInventoryResult = inventoryRedisExecutor.executorAddInventory(inventoryDealKeyCreator.getInventoryKey(inventoryCode), loadQuantity);
		return addInventoryResult;
	}

	@Override
	protected AckConsumerResult doAckConsumer(String version) {
		HashMap<String, String> parameters = new HashMap<>();
		parameters.put(SkuDealConstant.WAIT_ACK, inventoryDealKeyCreator.getWaitAckName());
		parameters.put(SkuDealConstant.DEAD_ACK, inventoryDealKeyCreator.getDeadAckName());
		AckConsumerResult ackConsumerResult = inventoryRedisExecutor.executorAckInventory(inventoryDealKeyCreator.getVersionKey(version), parameters);
		return ackConsumerResult;
	}

	@Override
	public String dealMethodCode() {
		return "skuRedisDeal";
	}

	@Override
	public String dealMethodDesc() {
		return "redis上以sku方式扣减库存";
	}

	@Override public RedisTemplate getRedisTemplate()
	{
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate)
	{
		this.redisTemplate = redisTemplate;
	}

	@Override
	public InventoryRedisExecutor getInventoryRedisExecutor() {
		return inventoryRedisExecutor;
	}

	public void setInventoryRedisExecutor(InventoryRedisExecutor inventoryRedisExecutor) {
		this.inventoryRedisExecutor = inventoryRedisExecutor;
	}

	@Override
	public void afterPropertiesSetDo() {
		if (inventoryRedisExecutor == null) {
			inventoryRedisExecutor = new SkuDealRedisExecutor(redisTemplate);
		}
		super.afterPropertiesSetDo();

	}

	public InventoryDealKeyCreator getInventoryDealKeyCreator() {
		return inventoryDealKeyCreator;
	}

	public void setInventoryDealKeyCreator(InventoryDealKeyCreator inventoryDealKeyCreator) {
		this.inventoryDealKeyCreator = inventoryDealKeyCreator;
	}

	public int getOneTimeMaxFreezeSkuNum() {
		return oneTimeMaxFreezeSkuNum;
	}

	public void setOneTimeMaxFreezeSkuNum(int oneTimeMaxFreezeSkuNum) {
		this.oneTimeMaxFreezeSkuNum = oneTimeMaxFreezeSkuNum;
	}
}
