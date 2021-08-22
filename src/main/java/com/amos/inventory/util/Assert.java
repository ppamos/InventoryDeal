package com.amos.inventory.util;

import com.amos.inventory.exception.ParameterException;

/**
 * @author amos
 * @since 2021/8/22 11:51
 */
public abstract class Assert {
    public final static void noNull(Object o) {
        if (o == null) {
            throw new ParameterException("parameter is null");
        }
    }

    public final static void noNull(Object o, String message) {
        if (o == null) {
            throw new ParameterException(message);
        }
    }

    public static void isTrue(boolean b,String message) {
        if (!b)
        {
            throw new ParameterException(message);
        }
    }
}
