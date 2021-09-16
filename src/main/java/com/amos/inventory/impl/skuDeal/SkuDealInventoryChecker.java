package com.amos.inventory.impl.skuDeal;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amos.inventory.constant.skuDeal.SkuDealConstant;
import com.amos.inventory.core.ActualCheck;
import com.amos.inventory.core.InventoryChecker;
import com.amos.inventory.core.InventoryDealKeyCreator;
import com.amos.inventory.redis.RedisTemplate;
import com.amos.inventory.exception.InventoryLockException;
import com.amos.inventory.lock.InventoryLock;
import com.amos.inventory.lock.InventoryLockFactory;
import com.amos.inventory.lock.InventoryLockKeyCreator;
import com.amos.inventory.util.CollectionUtils;
import com.amos.inventory.util.DateUtils;
import com.amos.inventory.util.StringUtils;

public class SkuDealInventoryChecker implements InventoryChecker {
	private RedisTemplate redisTemplate;

	private InventoryDealKeyCreator inventoryDealKeyCreator;

	private InventoryLockFactory inventoryLockFactory;

	private InventoryLockKeyCreator inventoryLockKeyCreator;

	private int maxCheckFreezeTimes = 3;

	private int maxWaitAckTimes = 3;

	private ActualCheck actualCheck;

	private final static Logger LOG = LoggerFactory.getLogger(SkuDealInventoryChecker.class);

	public void freezeCheck() {
		Date now = DateUtils.getNow();
		Set<String> versionKeys = redisTemplate.zRangeByScore(inventoryDealKeyCreator.getCheckFreezeName(), 0, now.getTime());
		if (CollectionUtils.isNotEmpty(versionKeys)) {
			for (String versionKey : versionKeys) {
				try {
					int currentCheckFreezeTimes = 0;
					String version = inventoryDealKeyCreator.getVersionByVersionKey(versionKey);
					String checkFreezeTimesString = redisTemplate.hGet(versionKey, SkuDealConstant.CHECK_FREEZE_TIMES_NAME);
					if (StringUtils.isNotEmpty(checkFreezeTimesString)) {
						currentCheckFreezeTimes = Integer.parseInt(checkFreezeTimesString) + 1;
					}
					if (currentCheckFreezeTimes > maxCheckFreezeTimes) {
						InventoryLock lock = null;
						try {
							lock = inventoryLockFactory.getLock(inventoryLockKeyCreator.getGlobalKey(version));
							lock.lock();
							redisTemplate.zRem(inventoryDealKeyCreator.getCheckFreezeName(), versionKey);
							redisTemplate.sAdd(inventoryDealKeyCreator.getDeadCheckFreezeName(), versionKey);
						} catch (InventoryLockException e) {
							LOG.error("checkFreeze error get lock fail", e);
						} finally {
							if (null != lock) {
								lock.releaseLock();
							}
						}
						continue;
					}
					redisTemplate.hIncrBy(versionKey, SkuDealConstant.CHECK_FREEZE_TIMES_NAME, 1);
					actualCheck.doCheckFreeze(version);
				} catch (Exception e) {
					LOG.error("freezeCheckFail versionKey{}", versionKey, e);
				}
			}
		}
	}

	public void ackCheck() {
		Set<String> versionKeys = redisTemplate.sMembers(inventoryDealKeyCreator.getWaitAckName());
		if (CollectionUtils.isNotEmpty(versionKeys)) {
			for (String versionKey : versionKeys) {
				try {
					int currentWaitAckTimes = 0;
					String version = inventoryDealKeyCreator.getVersionByVersionKey(versionKey);
					String waitAckTimeString = redisTemplate.hGet(versionKey, SkuDealConstant.WAIT_ACK_TIMES_NAME);
					if (StringUtils.isNotEmpty(waitAckTimeString)) {
						currentWaitAckTimes = Integer.parseInt(waitAckTimeString) + 1;
					}
					if (currentWaitAckTimes > maxWaitAckTimes) {
						InventoryLock lock = null;
						try {
							lock = inventoryLockFactory.getLock(inventoryLockKeyCreator.getGlobalKey(version));
							lock.lock();
							redisTemplate.sMove(inventoryDealKeyCreator.getWaitAckName(), inventoryDealKeyCreator.getDeadAckName(), versionKey);
						} catch (InventoryLockException e) {
							LOG.error("ackCheck error get lock fail", e);
						} finally {
							if (null != lock) {
								lock.releaseLock();
							}
						}
						continue;
					}
					redisTemplate.hIncrBy(versionKey, SkuDealConstant.WAIT_ACK_TIMES_NAME, 1);
					actualCheck.doAckCheck(version);
				} catch (Exception e) {
					LOG.error("ackCheckFail versionKey{}", versionKey, e);
				}
			}
		}
	}

	public void deadFreezeCheck() {
		Set<String> versionKeys = redisTemplate.sMembers(inventoryDealKeyCreator.getDeadCheckFreezeName());
		if (CollectionUtils.isNotEmpty(versionKeys)) {
			for (String versionKey : versionKeys) {
				try {
					String version = inventoryDealKeyCreator.getVersionByVersionKey(versionKey);
					actualCheck.doDeadFreezeCheck(version);
				} catch (Exception e) {
					LOG.error("deadFreezeCheckFail versionKey{}", versionKey, e);
				}

			}
		}
	}

	public void deadAckCheck() {
		Set<String> versionKeys = redisTemplate.sMembers(inventoryDealKeyCreator.getDeadAckName());
		if (CollectionUtils.isNotEmpty(versionKeys)) {
			for (String versionKey : versionKeys) {
				try {
					String version = inventoryDealKeyCreator.getVersionByVersionKey(versionKey);
					actualCheck.doDeadAckCheck(version);
				} catch (Exception e) {
					LOG.error("deadAckCheckFail versionKey{}", versionKey, e);
				}

			}
		}
	}
}
