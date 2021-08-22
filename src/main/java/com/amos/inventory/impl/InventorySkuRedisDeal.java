package com.amos.inventory.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amos.inventory.core.*;
import com.amos.inventory.result.*;
import com.amos.inventory.util.Assert;
import com.amos.inventory.util.MapUtils;

/**
 * @author amos
 * @since 2021/8/22 10:59
 */
public class InventorySkuRedisDeal extends AbstractInventoryDeal implements InventoryRedisDeal {

    private String magicCode;

    private String moduleEnglishName;

    private RedisTemplateFactory redisTemplateFactory;

    private InventoryRedisExecutor inventoryRedisExecutor;

    private RedisTemplate redisTemplate;

    private InventoryDealKeyCreator inventoryDealKeyCreator;

    // 一次冻结sku的个数
    private int oneTimeMaxFreezeSkuNum = 200;

    @Override
    protected ReleaseInventoryResult doReleaseInventory(String version) {
        return null;
    }

    @Override
    protected ConsumerInventoryResult doConsumerInventory(String version) {
        return null;
    }

    @Override
    protected FreezeInventoryResult doFreezeInventory(String version, Map<Long, Integer> inventoryNumMap,
        Map<String, String> parameters) {
        InventoryNumMapSpiltResult inventoryNumMapSpiltResult = this.splitInventoryNum(inventoryNumMap);
        HashMap<String, String> executorParameters = new HashMap<>();
        String checkFreezeName = inventoryDealKeyCreator.getCheckFreezeName();
        executorParameters.put("checkFreezeName", checkFreezeName);
        FreezeInventoryResult freezeInventoryResult =
            inventoryRedisExecutor.executorFreezeInventory(inventoryDealKeyCreator.getVersionKey(version),
                inventoryNumMapSpiltResult.getInventoryKeyList(), inventoryNumMapSpiltResult.getInventoryNum(), executorParameters);
        return freezeInventoryResult;
    }

    private InventoryNumMapSpiltResult splitInventoryNum(Map<Long, Integer> inventoryNumMap) {
        Assert.isTrue(MapUtils.isNotEmpty(inventoryNumMap), "inventoryNumMap is null");
        InventoryNumMapSpiltResult inventoryNumMapSpiltResult = new InventoryNumMapSpiltResult();
        List<String> inventoryKeyList = new ArrayList<String>();
        List<Integer> numList = new ArrayList<>();
        for (Long inventoryId : inventoryNumMap.keySet()) {
            Assert.noNull(inventoryId, "inventoryId is null");
            Integer num = inventoryNumMap.get(inventoryId);
            Assert.isTrue(num != null && num > 0, "inventory num is null");
            numList.add(num);
            String inventoryKey = inventoryDealKeyCreator.getInventoryKey(inventoryId);
            inventoryKeyList.add(inventoryKey);
        }
        inventoryNumMapSpiltResult.setInventoryKeyList(inventoryKeyList);
        inventoryNumMapSpiltResult.setInventoryNum(numList);
        return inventoryNumMapSpiltResult;
    }

    @Override
    protected AddInventoryResult doAddInventory(InventoryLoaderResult inventoryLoaderResult) {
        return null;
    }

    @Override
    protected AckConsumerResult doAckConsumer(String version) {
        return null;
    }

    @Override
    public String dealMethodCode() {
        return "skuRedisDeal";
    }

    @Override
    public String dealMethodDesc() {
        return "redis上以sku方式扣减库存";
    }

    @Override
    public String getMagicCode() {
        return magicCode;
    }

    @Override
    public String getModuleEnglishName() {
        return moduleEnglishName;
    }

    @Override
    public RedisTemplateFactory getRedisTemplateFactory() {
        return redisTemplateFactory;
    }

    public void setRedisTemplateFactory(RedisTemplateFactory redisTemplateFactory) {
        this.redisTemplateFactory = redisTemplateFactory;
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
        this.redisTemplate = redisTemplateFactory.getRedisTemplate();
        Assert.noNull(redisTemplate, "get redisTemplate error");
        super.afterPropertiesSetDo();

    }
}
