package com.gobravery.format.poi.excel.utils;

public class RowActionUtil {
	public static boolean eval(String val1, String val2, String action) {
		// 忽略全为空
		if (StringLike.empty(val1) && StringLike.empty(val2)) {
			return false;
		}
		String a = val1;
		String b = val2;
		//
		if (a == b || a.equals(b)) {
			return true;
		}
		// %__
		StringLike sl = new StringLike();
		return sl.like(a, b, action);
	}
}
