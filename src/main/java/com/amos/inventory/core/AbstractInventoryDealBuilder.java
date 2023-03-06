package com.amos.inventory.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.amos.inventory.listener.AddInventoryListener;
import com.amos.inventory.listener.ConsumerInventoryListener;
import com.amos.inventory.listener.FreezeInventoryListener;
import com.amos.inventory.listener.ReleaseInventoryListener;
import com.amos.inventory.listener.sort.SmallToLargeComparator;
import com.amos.inventory.lock.InventoryLockKeyCreator;

/**
 * @author amos
 * @since 2021/8/22 10:48
 */
public abstract class AbstractInventoryDealBuilder<T extends AbstractInventoryDeal> {

    public T build()
    {
       T t= this.doBuild();
       t.afterPropertiesSetDo();
       return t;
    }

    protected abstract T doBuild();

}
