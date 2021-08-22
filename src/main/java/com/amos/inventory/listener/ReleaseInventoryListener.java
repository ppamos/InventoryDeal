package com.amos.inventory.listener;

import com.amos.inventory.result.ReleaseInventoryResult;

/**
 * @author amos
 * @since 2021/8/21 13:44
 */
public interface ReleaseInventoryListener extends InventoryListener {
    void afterReleaseInventory(String version, ReleaseInventoryResult releaseInventoryResult);

    void onErrorDo(String version, Exception e);

    void beforeReleaseInventory(String version);
}
