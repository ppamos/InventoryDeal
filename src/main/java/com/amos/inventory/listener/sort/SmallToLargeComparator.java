package com.amos.inventory.listener.sort;

import java.util.Comparator;

/**
 * @author amos
 * @since 2021/8/21 23:26
 */
public class SmallToLargeComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        int i1 = getOrder(o1);
        int i2 = getOrder(o2);
        return Integer.compare(i1,i2);
    }

    private int getOrder(Object o) {
        if (o instanceof Order)
        {
            Order order=(Order) o;
            return order.getOrder();
        }
        return Integer.MAX_VALUE;
    }
}
