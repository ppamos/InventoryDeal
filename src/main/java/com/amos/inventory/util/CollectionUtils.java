package com.amos.inventory.util;

import java.util.Collection;

public abstract class CollectionUtils {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    };

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    };
}
