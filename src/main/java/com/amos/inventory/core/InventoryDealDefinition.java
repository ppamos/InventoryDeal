package com.amos.inventory.core;

/**
 * @author amos
 * @since 2021/8/21 13:44
 */
public interface InventoryDealDefinition extends MagicCodeDefinition, ModuleDefinition {
    String dealMethodCode();

    String dealMethodDesc();
}
