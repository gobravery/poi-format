package com.gobravery.format.poi.excel;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelBuilder {
	Logger LOG = LoggerFactory.getLogger(ExcelBuilder.class);
	private HSSFSheet ws;
	private int rowOffset = 0;
	private HSSFRow currentRowTpl;
	private int currentRowStart;
	public ExcelBuilder(HSSFSheet ws) {
		super();
		this.ws = ws;
	}
	public void buildSimpleProperty(int rowIndex, Integer cellIndex, Object value) {
		setCellValue(ws.getRow(rowIndex).getCell(cellIndex), value);
	}
	/**
	 * @param rowIndex
	 * @param rowCount
	 */
	public void prepareRows(int rowIndex, int rowCount) {
		currentRowStart = rowIndex + rowOffset;
		HSSFRow tplRow = ws.getRow(currentRowStart);
		if(tplRow==null){
			tplRow = initRow(currentRowStart);
		}
		currentRowTpl = tplRow;
		if(rowCount > 0) {
			/**
			 *在于1时需要对行进行初始化 
			 * ws.getLastRowNum()
			 */
			int allNumber=getAllNumber();
			ws.shiftRows(currentRowStart + 1, allNumber, rowCount - 1, true, true);
			for(int i = 0; i < rowCount - 1; i++) {
				initRow(tplRow, i);
			}
			rowOffset = rowOffset + rowCount - 1;
		}
		if(rowCount == 0){
			int allNumber=getAllNumber();
			ws.shiftRows(currentRowStart + 1,allNumber, -1);
			initRow(tplRow, 0);
		}
	}
	private int getAllNumber(){
		if(ws.getLastRowNum()>1){
			return ws.getLastRowNum();
		}else{
			return ws.getPhysicalNumberOfRows();
		}
	}
	private HSSFRow initRow(int i) {
		HSSFRow row = ws.getRow(i);
		if(row == null) row = ws.createRow(i);
		row.setHeight((short)520);
		return row;
	}
	private void initRow(HSSFRow tplRow, int i) {
		HSSFRow row = ws.getRow(currentRowStart + i + 1);
		if(row == null) row = ws.createRow(currentRowStart + i + 1);
		row.setHeight(tplRow.getHeight());
	}
	public void buildRowProperty(int rowIndex, Integer cellIndex, int colSpan, Object value) {
		int trueRowIndex = currentRowStart + rowIndex;
		HSSFRow row = ws.getRow(trueRowIndex);
		LOG.info("trueRowIndex>>>"+trueRowIndex);
		for(short i = 0; i < colSpan; i++) {
			HSSFCell cell = row.getCell((cellIndex + i));
			if(cell == null) {
				cell = row.createCell((cellIndex + i));
			}
		}
		if(colSpan > 1) {
			ws.addMergedRegion(new CellRangeAddress(trueRowIndex,trueRowIndex,cellIndex.shortValue(), (cellIndex + colSpan - 1)));
		}
		for(int i = 0; i < colSpan; i++) {
			HSSFCell cellTpl = currentRowTpl.getCell((cellIndex + i));
			HSSFCell cell = row.getCell((cellIndex + i));
			cell.setCellStyle(cellTpl.getCellStyle());
		}
		setCellValue(row.getCell(cellIndex), value);
		
	}
	private void setCellValue(HSSFCell cell, Object value) {
		if(value instanceof Date) {
			cell.setCellValue((Date) value);
		} else if(value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if(value instanceof String) {
			cell.setCellValue((String) value);
		}
	}
}
