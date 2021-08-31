package com.amos.inventory.util;

/**
 * @author amos
 * @since 2021/8/30 0030 22:40
 */
public abstract class StringUtils {
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}
}
