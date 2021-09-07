package com.amos.inventory.util;

import java.util.Collection;
import java.util.Map;

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

	public static void isTrue(boolean b, String message) {
		if (!b) {
			throw new ParameterException(message);
		}
	}

	public static void notEmptyString(String s, String message) {
		if (StringUtils.isEmpty(s)) {
			throw new ParameterException(message);
		}
	}

	public static void isEmpty(String s, String message) {
		if (StringUtils.isNotEmpty(message)) {
			throw new ParameterException(message);
		}
	}

	public static void notEmptyCollection(Collection c, String message) {
		if (CollectionUtils.isEmpty(c)) {
			throw new ParameterException(message);
		}
	}

	public static void notEmptyMap(Map m,String message)
	{
		if(MapUtils.isEmpty(m))
		{
			throw new ParameterException(message);
		}
	}
}
