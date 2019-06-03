package com.gobravery.format.poi.excel.row;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.gobravery.format.poi.excel.RowPropertyConfig;
import com.gobravery.format.poi.excel.utils.RowActionUtil;

public class RowSpanPostCall implements RowPostCallBack {

	public void postBuild(HSSFSheet ws, RowPropertyConfig config) {
		// TODO Auto-generated method stub
		String rowSpanVal="";
		int rowSpanStartIndex=-1;
		int rowSpanEndIndex=-1;
		int endRowIndex=ws.getLastRowNum();
		for(int i=config.getRowIndex();i<endRowIndex;i++){
			HSSFRow ro =ws.getRow(i);
			HSSFCell c=ro.getCell(config.getCellIndex());
			String curVal=c.getStringCellValue();
			//
			if(i==config.getRowIndex()){
				rowSpanVal=curVal;
			}
			//System.out.println("rowSpanStartIndex"+rowSpanStartIndex+",rowSpanEndIndex"+rowSpanEndIndex);
			//
			if(RowActionUtil.eval(curVal, rowSpanVal, config.getCondition())){
				//相同时
				if(rowSpanStartIndex<0){
					rowSpanStartIndex=i;
				}else{
					rowSpanEndIndex=i;
				}
			}else{
				//不相同时
				if(rowSpanEndIndex> rowSpanStartIndex && rowSpanStartIndex>0){
					//存在需要合并的
					ws.addMergedRegion(new CellRangeAddress(rowSpanStartIndex,rowSpanEndIndex, config.getCellIndex(), config.getCellIndex()));
				}
				//重新查找
				rowSpanStartIndex = i;
				rowSpanEndIndex = -1;
				rowSpanVal = curVal;
			}
			
		}
	}

}
