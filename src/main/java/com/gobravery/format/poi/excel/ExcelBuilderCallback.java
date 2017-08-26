package com.gobravery.format.poi.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface ExcelBuilderCallback {
	void preBuild(HSSFSheet ws);
	void postBuild(HSSFSheet ws);
}
