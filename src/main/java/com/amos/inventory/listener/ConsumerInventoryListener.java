package com.amos.inventory.listener;

import com.amos.inventory.result.ConsumerInventoryResult;


/**
 * @author amos
 * @since 2021/8/21 13:44
 */
public interface ConsumerInventoryListener extends InventoryListener {
    void afterConsumerInventory(String version, ConsumerInventoryResult consumerInventoryResult);

    void onConsumerErrorDo(String version, Exception e);

    void beforeConsumerInventory(String version);
}
