package com.amos.inventory.impl.skuDeal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.amos.inventory.core.*;
import com.amos.inventory.listener.*;
import com.amos.inventory.listener.sort.SmallToLargeComparator;
import com.amos.inventory.lock.DefaultInventoryLockKeyCreator;
import com.amos.inventory.lock.InventoryLockFactory;
import com.amos.inventory.lock.InventoryLockKeyCreator;
import com.amos.inventory.redis.InventoryRedisExecutor;
import com.amos.inventory.redis.RedisTemplate;
import com.amos.inventory.redis.RedisTemplateFactory;

public final class InventorySkuRedisDealBuilder extends AbstractInventoryDealBuilder<InventorySkuRedisDeal> {
	private List<AddInventoryListener> addInventoryListeners = new ArrayList<>();

	private List<FreezeInventoryListener> freezeInventoryListeners = new ArrayList<>();

	private List<ConsumerInventoryListener> consumerInventoryListeners = new ArrayList<>();

	private List<ReleaseInventoryListener> releaseInventoryListeners = new ArrayList<>();

    private List<AckInventoryListener> ackInventoryListeners = new ArrayList<>();

    private Comparator comparator = new SmallToLargeComparator();

    private InventoryLockFactory inventoryLockFactory;

    private InventoryLockKeyCreator keyCreator = new DefaultInventoryLockKeyCreator();

    private InventoryDealKeyCreator inventoryDealKeyCreator;

    private InventoryDealValidator validator = new DefaultSkuInventoryDealValidator();

	private InventoryRedisExecutor inventoryRedisExecutor;

	private RedisTemplate redisTemplate;

	private InventorySkuRedisDealBuilder() {
	}

	public static InventorySkuRedisDealBuilder anInventorySkuRedisDeal() {
		return new InventorySkuRedisDealBuilder();
	}

	public InventorySkuRedisDealBuilder withRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
		return this;
	}

	public InventorySkuRedisDealBuilder withInventoryRedisExecutor(InventoryRedisExecutor inventoryRedisExecutor) {
		this.inventoryRedisExecutor = inventoryRedisExecutor;
		return this;
	}

	public InventorySkuRedisDealBuilder listener(AddInventoryListener addInventoryListener) {
		this.addInventoryListeners.add(addInventoryListener);
		return this;
	}

	public InventorySkuRedisDealBuilder listener(FreezeInventoryListener freezeInventoryListener) {
		this.freezeInventoryListeners.add(freezeInventoryListener) ;
		return this;
	}

	public InventorySkuRedisDealBuilder listener(ConsumerInventoryListener consumerInventoryListener) {
		this.consumerInventoryListeners.add(consumerInventoryListener);
		return this;
	}

	public InventorySkuRedisDealBuilder listener(ReleaseInventoryListener releaseInventoryListener) {
		this.releaseInventoryListeners.add(releaseInventoryListener);
		return this;
	}

    public InventorySkuRedisDealBuilder listener(AckInventoryListener ackInventoryListener) {
        this.ackInventoryListeners.add(ackInventoryListener);
        return this;
    }

	public InventorySkuRedisDealBuilder withComparator(Comparator comparator) {
		this.comparator = comparator;
		return this;
	}

	public InventorySkuRedisDealBuilder withInventoryLockFactory(InventoryLockFactory inventoryLockFactory) {
		this.inventoryLockFactory = inventoryLockFactory;
		return this;
	}

	public InventorySkuRedisDealBuilder withKeyCreator(InventoryLockKeyCreator keyCreator) {
		this.keyCreator = keyCreator;
		return this;
	}

	public InventorySkuRedisDealBuilder withValidator(InventoryDealValidator validator) {
		this.validator = validator;
		return this;
	}

	public InventorySkuRedisDealBuilder withInventoryDealKeyCreator(InventoryDealKeyCreator inventoryDealKeyCreator) {
		this.inventoryDealKeyCreator = inventoryDealKeyCreator;
		return this;
	}


	@Override
	protected InventorySkuRedisDeal doBuild() {
		InventorySkuRedisDeal inventorySkuRedisDeal = new InventorySkuRedisDeal();
		inventorySkuRedisDeal.setRedisTemplate(redisTemplate);
		inventorySkuRedisDeal.setInventoryRedisExecutor(inventoryRedisExecutor);
		inventorySkuRedisDeal.setAddInventoryListeners(addInventoryListeners);
		inventorySkuRedisDeal.setFreezeInventoryListeners(freezeInventoryListeners);
		inventorySkuRedisDeal.setConsumerInventoryListeners(consumerInventoryListeners);
		inventorySkuRedisDeal.setReleaseInventoryListeners(releaseInventoryListeners);
		inventorySkuRedisDeal.setComparator(comparator);
		inventorySkuRedisDeal.setInventoryLockFactory(inventoryLockFactory);
		inventorySkuRedisDeal.setKeyCreator(keyCreator);
		inventorySkuRedisDeal.setValidator(validator);
		inventorySkuRedisDeal.setInventoryDealKeyCreator(inventoryDealKeyCreator);
		return inventorySkuRedisDeal;
	}
}
