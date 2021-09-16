package com.amos.inventory.lock;

import com.amos.inventory.util.Assert;

public class DefaultInventoryLockKeyCreator implements InventoryLockKeyCreator {

	private final static String GLOBAL_KEY_FORMAT = "inventory_deal_global_key_%s";

	private final static String ADD_INVENTORY_KEY_FORMAT = "add_inventory_key_%s";

	public DefaultInventoryLockKeyCreator() {
	}

	@Override
	public String getGlobalKey(String version) {
		Assert.notEmptyString(version, "version is empty");
		return String.format(GLOBAL_KEY_FORMAT, version);
	}

	@Override
	public String getAddInventoryKey(String inventoryCode) {
		Assert.notEmptyString(inventoryCode, "inventoryCode is empty");
		return String.format(ADD_INVENTORY_KEY_FORMAT, inventoryCode);
	}
}
