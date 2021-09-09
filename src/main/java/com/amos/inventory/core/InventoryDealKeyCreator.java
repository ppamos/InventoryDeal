package com.amos.inventory.core;

/**
 * @author amos
 * @since 2021/8/22 16:44
 */
public interface InventoryDealKeyCreator {

	String getInventoryKey(String inventoryCode);

	String getVersionKey(String version);

	String getCheckFreezeName();

	String getDeadCheckFreezeName();

	String getWaitAckName();

	String getDeadAckName();

	String getVersionByVersionKey(String versionKey);
}
