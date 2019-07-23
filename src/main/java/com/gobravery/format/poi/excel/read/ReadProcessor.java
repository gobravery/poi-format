package com.gobravery.format.poi.excel.read;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface ReadProcessor {
	void preBuild(HSSFSheet ws);
	void processor(Map<String,Object> row,HSSFSheet ws);
}
