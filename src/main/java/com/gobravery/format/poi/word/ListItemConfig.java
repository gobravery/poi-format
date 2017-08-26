package com.gobravery.format.poi.word;

import com.gobravery.format.poi.excel.CellType;

public class ListItemConfig {
	private short cellIndex;
	private int colSpan = 1;
	private	String propertyName;
	private CellType cellType = CellType.String; 
	
	public short getCellIndex() {
		return cellIndex;
	}
	public void setCellIndex(short cellIndex) {
		this.cellIndex = cellIndex;
	}
	public int getColSpan() {
		return colSpan;
	}
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public CellType getCellType() {
		return cellType;
	}
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
	 
	
}
