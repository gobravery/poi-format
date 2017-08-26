package com.gobravery.format.poi.excel;

public class SimplePropertyConfig {
	private int rowIndex;
	private short cellIndex;
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public short getCellIndex() {
		return cellIndex;
	}
	public void setCellIndex(short cellIndex) {
		this.cellIndex = cellIndex;
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
	private String propertyName;
	private CellType cellType = CellType.String;
	
}
