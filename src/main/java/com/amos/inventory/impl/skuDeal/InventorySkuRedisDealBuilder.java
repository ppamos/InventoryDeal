package com.amos.inventory.impl.skuDeal;

import com.amos.inventory.core.*;
import com.amos.inventory.impl.DefaultInventoryLockKeyCreator;
import com.amos.inventory.listener.AddInventoryListener;
import com.amos.inventory.listener.ConsumerInventoryListener;
import com.amos.inventory.listener.FreezeInventoryListener;
import com.amos.inventory.listener.ReleaseInventoryListener;
import com.amos.inventory.listener.sort.SmallToLargeComparator;
import com.amos.inventory.lock.InventoryLockFactory;
import com.amos.inventory.lock.InventoryLockKeyCreator;

import java.util.Comparator;
import java.util.List;

public final class InventorySkuRedisDealBuilder extends AbstractInventoryDealBuilder
{
    protected List<AddInventoryListener> addInventoryListeners;

    protected List<FreezeInventoryListener> freezeInventoryListeners;

    protected List<ConsumerInventoryListener> consumerInventoryListeners;

    protected List<ReleaseInventoryListener> releaseInventoryListeners;

    protected Comparator comparator=new SmallToLargeComparator();

    protected InventoryLockFactory inventoryLockFactory;

    protected InventoryLockKeyCreator keyCreator=new DefaultInventoryLockKeyCreator();

    protected InventoryDealKeyCreator inventoryDealKeyCreator;

    protected InventoryDealValidator validator=new DefaultSkuInventoryDealValidator();


    private RedisTemplateFactory redisTemplateFactory;

    private InventoryRedisExecutor inventoryRedisExecutor;

    private InventorySkuRedisDealBuilder()
    {
    }

    public static InventorySkuRedisDealBuilder anInventorySkuRedisDeal()
    {
        return new InventorySkuRedisDealBuilder();
    }

    public InventorySkuRedisDealBuilder withRedisTemplateFactory(RedisTemplateFactory redisTemplateFactory)
    {
        this.redisTemplateFactory = redisTemplateFactory;
        return this;
    }

    public InventorySkuRedisDealBuilder withInventoryRedisExecutor(InventoryRedisExecutor inventoryRedisExecutor)
    {
        this.inventoryRedisExecutor = inventoryRedisExecutor;
        return this;
    }

    public InventorySkuRedisDealBuilder withAddInventoryListeners(List<AddInventoryListener> addInventoryListeners)
    {
        this.addInventoryListeners = addInventoryListeners;
        return this;
    }

    public InventorySkuRedisDealBuilder withFreezeInventoryListeners(List<FreezeInventoryListener> freezeInventoryListeners)
    {
        this.freezeInventoryListeners = freezeInventoryListeners;
        return this;
    }

    public InventorySkuRedisDealBuilder withConsumerInventoryListeners(List<ConsumerInventoryListener> consumerInventoryListeners)
    {
        this.consumerInventoryListeners = consumerInventoryListeners;
        return this;
    }

    public InventorySkuRedisDealBuilder withReleaseInventoryListeners(List<ReleaseInventoryListener> releaseInventoryListeners)
    {
        this.releaseInventoryListeners = releaseInventoryListeners;
        return this;
    }

    public InventorySkuRedisDealBuilder withComparator(Comparator comparator)
    {
        this.comparator = comparator;
        return this;
    }

    public InventorySkuRedisDealBuilder withInventoryLockFactory(InventoryLockFactory inventoryLockFactory)
    {
        this.inventoryLockFactory = inventoryLockFactory;
        return this;
    }

    public InventorySkuRedisDealBuilder withKeyCreator(InventoryLockKeyCreator keyCreator)
    {
        this.keyCreator = keyCreator;
        return this;
    }

    public InventorySkuRedisDealBuilder withValidator(InventoryDealValidator validator)
    {
        this.validator = validator;
        return this;
    }

    public InventorySkuRedisDealBuilder withInventoryDealKeyCreator(InventoryDealKeyCreator inventoryDealKeyCreator)
    {
        this.inventoryDealKeyCreator=inventoryDealKeyCreator;
        return this;
    }

    @Override protected AbstractInventoryDeal doBuild()
    {
        InventorySkuRedisDeal inventorySkuRedisDeal = new InventorySkuRedisDeal();
        inventorySkuRedisDeal.setRedisTemplateFactory(redisTemplateFactory);
        inventorySkuRedisDeal.setInventoryRedisExecutor(inventoryRedisExecutor);
        inventorySkuRedisDeal.setAddInventoryListeners(addInventoryListeners);
        inventorySkuRedisDeal.setFreezeInventoryListeners(freezeInventoryListeners);
        inventorySkuRedisDeal.setConsumerInventoryListeners(consumerInventoryListeners);
        inventorySkuRedisDeal.setReleaseInventoryListeners(releaseInventoryListeners);
        inventorySkuRedisDeal.setComparator(comparator);
        inventorySkuRedisDeal.setInventoryLockFactory(inventoryLockFactory);
        inventorySkuRedisDeal.setKeyCreator(keyCreator);
        inventorySkuRedisDeal.setValidator(validator);
        return inventorySkuRedisDeal;
    }
}
