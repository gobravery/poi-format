package com.gobravery.format.poi.excel.row;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.gobravery.format.poi.excel.RowPropertyConfig;

public interface RowPostCallBack {
	public void postBuild(HSSFSheet ws,RowPropertyConfig config);
}
