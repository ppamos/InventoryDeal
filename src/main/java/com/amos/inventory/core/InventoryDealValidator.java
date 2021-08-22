package com.amos.inventory.core;

/**
 * @author amos
 * @since 2021/8/21 23:55
 */
public interface InventoryDealValidator<T extends AbstractInventoryDeal> {
    void verify(T t);
}
