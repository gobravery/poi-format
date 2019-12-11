package com.gobravery.format.poi.word;

import com.gobravery.format.poi.excel.CellType;
import com.gobravery.format.poi.word.config.ValueConfig;

public class ListItemConfig implements ValueConfig {
	private short cellIndex;
	private int colSpan = 1;
	private	String propertyName;
	private CellType cellType = CellType.String; 
	//用于图片的宽高
	private Integer width=0;
	private Integer height=0;
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
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
}
