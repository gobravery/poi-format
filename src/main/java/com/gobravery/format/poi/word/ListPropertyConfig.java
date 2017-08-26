package com.gobravery.format.poi.word;

import java.util.ArrayList;
import java.util.List;

public class ListPropertyConfig {
	private String table;
	private RowNext rowNext=RowNext.Add;
	private int row=0;
	private String propertyName;
	private List<ListItemConfig> itemConfig = new ArrayList<ListItemConfig>();
	public void addItemConfig(ListItemConfig config) {
		itemConfig.add(config);
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public List<ListItemConfig> getItemConfig() {
		return itemConfig;
	}
	public void setItemConfig(List<ListItemConfig> itemConfig) {
		this.itemConfig = itemConfig;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
	public RowNext getRowNext() {
		return rowNext;
	}
	public void setRowNext(RowNext rowNext) {
		this.rowNext = rowNext;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	
}
