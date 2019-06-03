package com.gobravery.format.poi.excel;

public class RowPropertyConfig {
	private int rowIndex;
	private int cellIndex;
	/**
	 * 
	 *% =全匹配 
	 * _=匹配第一个字符
	 */
	private String condition;
	
	private RowAction  action= RowAction.RowSpan;
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getCellIndex() {
		return cellIndex;
	}
	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public RowAction getAction() {
		return action;
	}
	public void setAction(RowAction action) {
		this.action = action;
	}
	
}
