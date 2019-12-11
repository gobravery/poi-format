package com.gobravery.format.poi.word;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.digester.Digester;

import com.gobravery.format.poi.excel.CellType;


public class WordBuilderConfig {
	static {
		ConvertUtils.register(new Converter(){
			public Object convert(Class clz, Object value) {
				return CellType.valueOf(value.toString());
			}
			
		}, CellType.class);
		ConvertUtils.register(new Converter(){
			public Object convert(Class clz, Object value) {
				return RowNext.valueOf(value.toString());
			}
			
		}, RowNext.class);
	}
	private List<SimplePropertyConfig> simplePropertyConfig = new ArrayList<SimplePropertyConfig>();
	private List<TablePropertyConfig> tablePropertyConfig = new ArrayList<TablePropertyConfig>();
	private List<ListPropertyConfig> listPropertyConfig = new ArrayList<ListPropertyConfig>();
	public List<SimplePropertyConfig> getSimplePropertyConfig() {
		return simplePropertyConfig;
	}
	public List<TablePropertyConfig> getTablePropertyConfig() {
		return tablePropertyConfig;
	}
	public void addSimplePropertyConfig(SimplePropertyConfig config) {
		simplePropertyConfig.add(config);
	}
	public void addTablePropertyConfig(TablePropertyConfig config) {
		tablePropertyConfig.add(config);
	}
	public void addListPropertyConfig(ListPropertyConfig config) {
		listPropertyConfig.add(config);
	}
	public void setSimplePropertyConfig(
			List<SimplePropertyConfig> simplePropertyConfig) {
		this.simplePropertyConfig = simplePropertyConfig;
	}
	public void setTablePropertyConfig(
			List<TablePropertyConfig> tablePropertyConfig) {
		this.tablePropertyConfig = tablePropertyConfig;
	}
	public List<ListPropertyConfig> getListPropertyConfig() {
		return listPropertyConfig;
	}

	public void setListPropertyConfig(List<ListPropertyConfig> listPropertyConfig) {
		this.listPropertyConfig = listPropertyConfig;
	}
	
	public static WordBuilderConfig parse(InputStream is) throws Exception {
		Digester d = new Digester();
		d.addObjectCreate("config", WordBuilderConfig.class);
		d.addObjectCreate("config/simpleProperties/property", SimplePropertyConfig.class);
		d.addSetProperties("config/simpleProperties/property");
		d.addSetNext("config/simpleProperties/property", "addSimplePropertyConfig");
		//
		d.addObjectCreate("config/tableProperties/property", TablePropertyConfig.class);
		d.addSetProperties("config/tableProperties/property");
		d.addSetNext("config/tableProperties/property", "addTablePropertyConfig");
		//
		d.addObjectCreate("config/listProperties/property", ListPropertyConfig.class);
		d.addSetProperties("config/listProperties/property");
		//
		d.addObjectCreate("config/listProperties/property/item", ListItemConfig.class);
		d.addSetProperties("config/listProperties/property/item");
		//
		d.addSetNext("config/listProperties/property/item", "addItemConfig");
		d.addSetNext("config/listProperties/property", "addListPropertyConfig");
		WordBuilderConfig cfg = (WordBuilderConfig) d.parse(is);
		return cfg;
	}
	
}
