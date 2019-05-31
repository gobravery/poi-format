package com.gobravery.format.poi.excel.utils;

public class RowActionUtil {
	public static boolean eval(String val1,String val2,String action){
		//忽略全为空
		if(empty(val1) && empty(val2)){
			return false;
		}
		String a=val1;
		String b=val2;
		if(action.equals("%")){
			//全匹配
		}
		//TODO %__
		
		//
		if(a==b || a.equals(b)){
			return true;
		}
		return false;
	}
	private static boolean empty(String v){
		return v==null || v.equals("");
	}
}
