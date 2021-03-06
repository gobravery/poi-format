package com.gobravery.format.poi.excel;

import java.util.ArrayList;
import java.util.List;

public class ListPropertyConfig {
	private int tplRow;
	private String propertyName;
	private List<ListItemConfig> itemConfig = new ArrayList<ListItemConfig>();
	public void addItemConfig(ListItemConfig config) {
		itemConfig.add(config);
	}
	public int getTplRow() {
		return tplRow;
	}
	public void setTplRow(int tplRow) {
		this.tplRow = tplRow;
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
	
}
