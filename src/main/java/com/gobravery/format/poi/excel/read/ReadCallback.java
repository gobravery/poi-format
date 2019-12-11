package com.gobravery.format.poi.excel.read;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface ReadCallback {
	void preBuild(HSSFSheet ws);
	void postBuild(List<Map<String,Object>> result,HSSFSheet ws);
}
