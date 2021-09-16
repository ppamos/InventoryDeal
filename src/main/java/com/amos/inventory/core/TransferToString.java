package com.amos.inventory.core;

@FunctionalInterface
public interface TransferToString<T>
{
    String apply(T t);
}
