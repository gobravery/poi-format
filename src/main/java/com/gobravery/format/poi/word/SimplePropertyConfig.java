package com.gobravery.format.poi.word;

import com.gobravery.format.poi.excel.CellType;

public class SimplePropertyConfig {
	
	private String propertyName;
	private CellType cellType = CellType.String;
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
