package com.gobravery.format.poi.excel.read;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface ReadProcessor {
	void preBuild(HSSFSheet ws);
	/**
	 * @param row
	 * @param ws
	 * @return false 表示中断、结束
	 */
	boolean processor(Map<String,Object> row,HSSFSheet ws);
}
