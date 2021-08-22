package com.amos.inventory.util;

import java.util.Map;

/**
 * @author amos
 * @since 2021/8/22 15:16
 */
public abstract class MapUtils {
    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
}
