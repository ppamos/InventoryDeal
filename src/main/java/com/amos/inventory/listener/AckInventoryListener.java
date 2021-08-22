package com.amos.inventory.listener;

import com.amos.inventory.result.AckConsumerResult;

/**
 * @author amos
 * @since 2021/8/21 13:44
 */
public interface AckInventoryListener extends InventoryListener {
    void beforeAck(String version);

    void afterAck(String version, AckConsumerResult ackConsumerResult);

    void onErrorDo(String version, Exception e);
}
