package com.gobravery.format.poi.excel;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.Region;

public class ExcelBuilder {
	private HSSFSheet ws;
	private int rowOffset = 0;
	private HSSFRow currentRowTpl;
	private int currentRowStart;
	public ExcelBuilder(HSSFSheet ws) {
		super();
		this.ws = ws;
	}
	public void buildSimpleProperty(int rowIndex, short cellIndex, Object value) {
		setCellValue(ws.getRow(rowIndex).getCell(cellIndex), value);
	}
	public void prepareRows(int rowIndex, int rowCount) {
		currentRowStart = rowIndex + rowOffset;
		HSSFRow tplRow = ws.getRow(currentRowStart);
		currentRowTpl = tplRow;
		if(rowCount > 1) {
			ws.shiftRows(currentRowStart + 1, ws.getLastRowNum(), rowCount - 1, true, true);
			for(int i = 0; i < rowCount - 1; i++) {
				HSSFRow row = ws.getRow(currentRowStart + i + 1);
				if(row == null) row = ws.createRow(currentRowStart + i + 1);
				row.setHeight(tplRow.getHeight());
			}
			rowOffset = rowOffset + rowCount - 1;
		
		}
		if(rowCount == 0){
			ws.shiftRows(currentRowStart + 1, ws.getLastRowNum(), -1);
		}
	}
	public void buildRowProperty(int rowIndex, short cellIndex, short colSpan, Object value) {
		int trueRowIndex = currentRowStart + rowIndex;
		HSSFRow row = ws.getRow(trueRowIndex);
		for(short i = 0; i < colSpan; i++) {
			HSSFCell cell = row.getCell((short) (cellIndex + i));
			if(cell == null) {
				cell = row.createCell((short) (cellIndex + i));
			}
		}
		if(colSpan > 1) {
			ws.addMergedRegion(new Region(trueRowIndex, cellIndex, trueRowIndex, (short) (cellIndex + colSpan - 1)));
		}
		for(int i = 0; i < colSpan; i++) {
			HSSFCell cellTpl = currentRowTpl.getCell((short) (cellIndex + i));
			HSSFCell cell = row.getCell((short) (cellIndex + i));
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
