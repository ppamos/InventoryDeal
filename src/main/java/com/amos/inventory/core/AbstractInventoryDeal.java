package com.amos.inventory.core;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amos.inventory.exception.InventoryLockException;
import com.amos.inventory.listener.*;
import com.amos.inventory.lock.InventoryLock;
import com.amos.inventory.lock.InventoryLockFactory;
import com.amos.inventory.lock.InventoryLockKeyCreator;
import com.amos.inventory.result.*;
import com.amos.inventory.util.CollectionUtils;

/**
 * @author amos
 * @since 2021/8/21 13:44
 */
public abstract class AbstractInventoryDeal implements InventoryDealDefinition, Initialization {

	protected Logger LOG = LoggerFactory.getLogger(this.getClass());

	protected List<AddInventoryListener> addInventoryListeners;

	protected List<FreezeInventoryListener> freezeInventoryListeners;

	protected List<ConsumerInventoryListener> consumerInventoryListeners;

	protected List<ReleaseInventoryListener> releaseInventoryListeners;

	protected List<AckInventoryListener> ackInventoryListeners;

	protected Comparator comparator;

	protected InventoryLockFactory inventoryLockFactory;

	protected InventoryLockKeyCreator keyCreator;

	protected InventoryDealValidator validator;

	private final static String GET_LOCK_ERROR_MESSAGE = "get Lock error";

	private final static String RELEASE_LOCK_ERROR_MESSAGE = "release Lock error";

	protected abstract ReleaseInventoryResult doReleaseInventory(String version);

	protected abstract ConsumerInventoryResult doConsumerInventory(String version);

	protected abstract FreezeInventoryResult doFreezeInventory(String version, Map<String, Integer> inventoryNumMap, Map<String, String> parameters);

	protected abstract AddInventoryResult doAddInventory(InventoryLoaderResult inventoryLoaderResult);

	protected abstract AckConsumerResult doAckConsumer(String version);

	public void addInventory(String inventoryCode, InventoryLoader inventoryLoader) throws InventoryLockException
	{
		InventoryLock inventoryLock = null;
		AddInventoryResult addInventoryResult = null;
		try {
			inventoryLock = inventoryLockFactory.getLock(keyCreator.getAddInventoryKey(inventoryCode));
			inventoryLock.lock();
			this.beforeAddInventory(inventoryCode);
			InventoryLoaderResult inventoryLoaderResult = null;

			try {
				inventoryLoaderResult = inventoryLoader.loadInventory(inventoryCode);
				addInventoryResult = this.doAddInventory(inventoryLoaderResult);
			} catch (Exception e) {
				this.onAddErrorDo(e, inventoryCode);
				throw e;
			}
			this.afterAddInventory(inventoryCode, addInventoryResult);
		} catch (InventoryLockException e) {
			LOG.error(GET_LOCK_ERROR_MESSAGE, e);
			throw e;
		} finally {
			if (null != inventoryLock) {
				try {
					inventoryLock.releaseLock();
				} catch (InventoryLockException e) {
					LOG.error(RELEASE_LOCK_ERROR_MESSAGE, e);
					throw e;
				}
			}

		}
	}

	public void freezeInventory(String version, Map<String, Integer> inventoryNumMap, Map<String, String> parameters)
			throws InventoryLockException
	{

		InventoryLock inventoryLock = null;
		FreezeInventoryResult freezeInventoryResult = null;
		try {
			inventoryLock = inventoryLockFactory.getLock(keyCreator.getGlobalKey(version));
			inventoryLock.lock();
			this.beforeFreezeInventory(version, inventoryNumMap, parameters);
			try {
				freezeInventoryResult = this.doFreezeInventory(version, inventoryNumMap, parameters);
			} catch (Exception e) {
				this.onFreezeErrorDo(version, inventoryNumMap, parameters, e);
				throw e;
			}
			this.afterFreezeInventory(version, inventoryNumMap, parameters, freezeInventoryResult);

		} catch (InventoryLockException e) {
			LOG.error(GET_LOCK_ERROR_MESSAGE, e);
			throw e;
		} finally {
			if (null != inventoryLock) {
				try {
					inventoryLock.releaseLock();
				} catch (InventoryLockException e) {
					LOG.error(RELEASE_LOCK_ERROR_MESSAGE, e);
					throw e;
				}
			}
		}
	}

	public void consumerInventory(String version) throws InventoryLockException
	{
		InventoryLock inventoryLock = null;
		ConsumerInventoryResult consumerInventoryResult = null;
		try {
			inventoryLock = inventoryLockFactory.getLock(keyCreator.getGlobalKey(version));
			inventoryLock.lock();
			this.beforeConsumerInventory(version);
			try {
				consumerInventoryResult = this.doConsumerInventory(version);
			} catch (Exception e) {
				this.onConsumerErrorDo(version, e);
				throw e;
			}
			this.afterConsumerInventory(version, consumerInventoryResult);

		} catch (InventoryLockException e) {
			LOG.error(GET_LOCK_ERROR_MESSAGE, e);
			throw e;
		} finally {
			if (null != inventoryLock) {
				try {
					inventoryLock.releaseLock();
				} catch (InventoryLockException e) {
					LOG.error(RELEASE_LOCK_ERROR_MESSAGE, e);
					throw e;
				}
			}
		}
	}

	public void releaseInventory(String version) throws InventoryLockException
	{
		InventoryLock inventoryLock = null;
		ReleaseInventoryResult releaseInventoryResult = null;
		try {
			inventoryLock = inventoryLockFactory.getLock(keyCreator.getGlobalKey(version));
			inventoryLock.lock();
			this.beforeReleaseInventory(version);
			try {
				releaseInventoryResult = this.doReleaseInventory(version);
			} catch (Exception e) {
				this.onReleaseErrorDo(version, e);
				throw e;
			}
			this.afterReleaseInventory(version, releaseInventoryResult);

		} catch (InventoryLockException e) {
			LOG.error(GET_LOCK_ERROR_MESSAGE, e);
			throw e;
		} finally {
			if (null != inventoryLock) {
				try {
					inventoryLock.releaseLock();
				} catch (InventoryLockException e) {
					LOG.error(RELEASE_LOCK_ERROR_MESSAGE, e);
					throw e;
				}
			}
		}
	}

	public void ackConsumer(String version) throws InventoryLockException
	{
		InventoryLock inventoryLock = null;
		AckConsumerResult ackConsumerResult = null;
		try {
			inventoryLock = inventoryLockFactory.getLock(keyCreator.getGlobalKey(version));
			inventoryLock.lock();
			this.beforeAckConsumer(version);
			try {
				ackConsumerResult = this.doAckConsumer(version);
			} catch (Exception e) {
				this.onAckErrorDo(version, e);
				throw e;
			}
			this.afterAckConsumer(version, ackConsumerResult);

		} catch (InventoryLockException e) {
			LOG.error(GET_LOCK_ERROR_MESSAGE, e);
			throw e;
		} finally {
			if (null != inventoryLock) {
				try {
					inventoryLock.releaseLock();
				} catch (InventoryLockException e) {
					LOG.error(RELEASE_LOCK_ERROR_MESSAGE, e);
					throw e;
				}
			}
		}
	}

	private void afterAckConsumer(String version, AckConsumerResult ackConsumerResult) {
		if (CollectionUtils.isNotEmpty(ackInventoryListeners)) {
			for (AckInventoryListener ackInventoryListener : ackInventoryListeners) {
				ackInventoryListener.afterAck(version, ackConsumerResult);
			}
		}
	}

	private void onAckErrorDo(String version, Exception e) {
		if (CollectionUtils.isNotEmpty(ackInventoryListeners)) {
			for (AckInventoryListener ackInventoryListener : ackInventoryListeners) {
				ackInventoryListener.onErrorDo(version, e);
			}
		}
	}

	private void beforeAckConsumer(String version) {
		if (CollectionUtils.isNotEmpty(ackInventoryListeners)) {
			for (AckInventoryListener ackInventoryListener : ackInventoryListeners) {
				ackInventoryListener.beforeAck(version);
			}
		}
	}

	private void afterReleaseInventory(String version, ReleaseInventoryResult releaseInventoryResult) {
		if (CollectionUtils.isNotEmpty(releaseInventoryListeners)) {
			for (ReleaseInventoryListener releaseInventoryListener : releaseInventoryListeners) {
				releaseInventoryListener.afterReleaseInventory(version, releaseInventoryResult);
			}
		}
	}

	private void onReleaseErrorDo(String version, Exception e) {
		if (CollectionUtils.isNotEmpty(releaseInventoryListeners)) {
			for (ReleaseInventoryListener releaseInventoryListener : releaseInventoryListeners) {
				releaseInventoryListener.onErrorDo(version, e);
			}
		}
	}

	private void beforeReleaseInventory(String version) {
		if (CollectionUtils.isNotEmpty(releaseInventoryListeners)) {
			for (ReleaseInventoryListener releaseInventoryListener : releaseInventoryListeners) {
				releaseInventoryListener.beforeReleaseInventory(version);
			}
		}
	}

	private void afterConsumerInventory(String version, ConsumerInventoryResult consumerInventoryResult) {
		if (CollectionUtils.isNotEmpty(consumerInventoryListeners)) {
			for (ConsumerInventoryListener consumerInventoryListener : consumerInventoryListeners) {
				consumerInventoryListener.afterConsumerInventory(version, consumerInventoryResult);
			}
		}
	}

	private void onConsumerErrorDo(String version, Exception e) {
		if (CollectionUtils.isNotEmpty(consumerInventoryListeners)) {
			for (ConsumerInventoryListener consumerInventoryListener : consumerInventoryListeners) {
				consumerInventoryListener.onConsumerErrorDo(version, e);
			}
		}
	}

	private void beforeConsumerInventory(String version) {
		if (CollectionUtils.isNotEmpty(consumerInventoryListeners)) {
			for (ConsumerInventoryListener consumerInventoryListener : consumerInventoryListeners) {
				consumerInventoryListener.beforeConsumerInventory(version);
			}
		}
	}

	private void afterFreezeInventory(String version, Map<String, Integer> inventoryNumMap, Map<String, String> parameters, FreezeInventoryResult freezeInventoryResult) {
		if (CollectionUtils.isNotEmpty(freezeInventoryListeners)) {
			for (FreezeInventoryListener freezeInventoryListener : freezeInventoryListeners) {
				freezeInventoryListener.afterFreezeInventory(version, inventoryNumMap, parameters, freezeInventoryResult);
			}
		}
	}

	private void onFreezeErrorDo(String version, Map<String, Integer> inventoryNumMap, Map<String, String> parameters, Exception e) {
		if (CollectionUtils.isNotEmpty(freezeInventoryListeners)) {
			for (FreezeInventoryListener freezeInventoryListener : freezeInventoryListeners) {
				freezeInventoryListener.onErrorDo(version, inventoryNumMap, parameters, e);
			}
		}
	}

	private void beforeFreezeInventory(String version, Map<String, Integer> inventoryNumMap, Map<String, String> parameters) {
		if (CollectionUtils.isNotEmpty(freezeInventoryListeners)) {
			for (FreezeInventoryListener freezeInventoryListener : freezeInventoryListeners) {
				freezeInventoryListener.beforeFreezeInventory(version, inventoryNumMap, parameters);
			}
		}
	}

	private void afterAddInventory(String inventoryCode, AddInventoryResult addInventoryResult) {
		if (CollectionUtils.isNotEmpty(addInventoryListeners)) {
			for (AddInventoryListener addInventoryListener : addInventoryListeners) {
				addInventoryListener.afterAddInventory(inventoryCode, addInventoryResult);
			}
		}
	}

	private void onAddErrorDo(Exception e, String inventoryCode) {
		if (CollectionUtils.isNotEmpty(addInventoryListeners)) {
			for (AddInventoryListener addInventoryListener : addInventoryListeners) {
				addInventoryListener.onErrorDo(inventoryCode, e);
			}
		}
	}

	private void beforeAddInventory(String inventoryCode) {
		if (CollectionUtils.isNotEmpty(addInventoryListeners)) {
			for (AddInventoryListener addInventoryListener : addInventoryListeners) {
				addInventoryListener.beforeAddInventory(inventoryCode);
			}
		}
	}

	public List<AddInventoryListener> getAddInventoryListeners() {
		return addInventoryListeners;
	}

	public void setAddInventoryListeners(List<AddInventoryListener> addInventoryListeners) {
		this.addInventoryListeners = addInventoryListeners;
	}

	public List<FreezeInventoryListener> getFreezeInventoryListeners() {
		return freezeInventoryListeners;
	}

	public void setFreezeInventoryListeners(List<FreezeInventoryListener> freezeInventoryListeners) {
		this.freezeInventoryListeners = freezeInventoryListeners;
	}

	public List<ConsumerInventoryListener> getConsumerInventoryListeners() {
		return consumerInventoryListeners;
	}

	public void setConsumerInventoryListeners(List<ConsumerInventoryListener> consumerInventoryListeners) {
		this.consumerInventoryListeners = consumerInventoryListeners;
	}

	public List<ReleaseInventoryListener> getReleaseInventoryListeners() {
		return releaseInventoryListeners;
	}

	public void setReleaseInventoryListeners(List<ReleaseInventoryListener> releaseInventoryListeners) {
		this.releaseInventoryListeners = releaseInventoryListeners;
	}

	public Comparator getComparator() {
		return comparator;
	}

	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}

	public InventoryLockFactory getInventoryLockFactory() {
		return inventoryLockFactory;
	}

	public void setInventoryLockFactory(InventoryLockFactory inventoryLockFactory) {
		this.inventoryLockFactory = inventoryLockFactory;
	}

	public InventoryLockKeyCreator getKeyCreator() {
		return keyCreator;
	}

	public void setKeyCreator(InventoryLockKeyCreator keyCreator) {
		this.keyCreator = keyCreator;
	}

	public InventoryDealValidator getValidator() {
		return validator;
	}

	public void setValidator(InventoryDealValidator validator) {
		this.validator = validator;
	}

	public static String getGetLockErrorMessage() {
		return GET_LOCK_ERROR_MESSAGE;
	}

	public static String getReleaseLockErrorMessage() {
		return RELEASE_LOCK_ERROR_MESSAGE;
	}

	@Override
	public void afterPropertiesSetDo() {
		validator.verify(this);
		addInventoryListeners.sort(comparator);
		freezeInventoryListeners.sort(comparator);
		consumerInventoryListeners.sort(comparator);
		releaseInventoryListeners.sort(comparator);
	}
}
