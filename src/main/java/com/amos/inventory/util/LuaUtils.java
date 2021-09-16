package com.amos.inventory.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amos.inventory.core.TransferToString;

public abstract class LuaUtils {
	public static String transformLuaTableString(List list, TransferToString transferToString) {
		StringBuilder tableString = new StringBuilder();
		tableString.append("{");
		for (int i = 0; i < list.size(); i++) {
			int j = i + 1;
			Object o = list.get(i);
			tableString.append("[").append(j).append("]").append("=").append("'").append(transferToString.apply(o)).append("'");
			if (j < list.size()) {
				tableString.append(",");
			}
		}
		tableString.append("}");
		return tableString.toString();
	}

	public static List<String> luaTableStringToList(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		String[] split = value.split("=");
		List<String> valueList = new ArrayList<>();
		for (int i = 0; i < split.length; i++) {
			if (i == 0) {
				continue;
			}
			valueList.add(split[i].split("'")[1]);
		}
		return valueList;
	}

}
