package com.amos.inventory.listener.sort;

public interface Order {
    default Integer getOrder()
    {
        return Integer.MAX_VALUE;
    };
}
