package com.gobravery.format.poi.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.digester3.Digester;


public class ExcelBuilderConfig {
	static {
		ConvertUtils.register(new Converter(){
			public Object convert(Class clz, Object value) {
				return CellType.valueOf(value.toString());
			}
			
		}, CellType.class);
		///
		ConvertUtils.register(new Converter(){
			public Object convert(Class clz, Object value) {
				return RowAction.valueOf(value.toString());
			}
			
		}, RowAction.class);
	}
	private List<SimplePropertyConfig> simplePropertyConfig = new ArrayList<SimplePropertyConfig>();
	private List<RowPropertyConfig> rowPropertyConfig = new ArrayList<RowPropertyConfig>();
	private List<ListPropertyConfig> listPropertyConfig = new ArrayList<ListPropertyConfig>();
	public List<SimplePropertyConfig> getSimplePropertyConfig() {
		return simplePropertyConfig;
	}

	public void addSimplePropertyConfig(SimplePropertyConfig config) {
		simplePropertyConfig.add(config);
	}
	public void addRowPropertyConfig(RowPropertyConfig config) {
		rowPropertyConfig.add(config);
	}
	public void addListPropertyConfig(ListPropertyConfig config) {
		listPropertyConfig.add(config);
	}
	public void setSimplePropertyConfig(
			List<SimplePropertyConfig> simplePropertyConfig) {
		this.simplePropertyConfig = simplePropertyConfig;
	}

	public List<ListPropertyConfig> getListPropertyConfig() {
		return listPropertyConfig;
	}

	public void setListPropertyConfig(List<ListPropertyConfig> listPropertyConfig) {
		this.listPropertyConfig = listPropertyConfig;
	}
	
	public List<RowPropertyConfig> getRowPropertyConfig() {
		return rowPropertyConfig;
	}

	public void setRowPropertyConfig(List<RowPropertyConfig> rowPropertyConfig) {
		this.rowPropertyConfig = rowPropertyConfig;
	}

	public static ExcelBuilderConfig parse(InputStream is) throws Exception {
		Digester d = new Digester();
		d.addObjectCreate("config", ExcelBuilderConfig.class);
		d.addObjectCreate("config/simpleProperties/property", SimplePropertyConfig.class);
		d.addSetProperties("config/simpleProperties/property");
		d.addSetNext("config/simpleProperties/property", "addSimplePropertyConfig");
		//
		d.addObjectCreate("config/listProperties/property", ListPropertyConfig.class);
		d.addSetProperties("config/listProperties/property");
		d.addObjectCreate("config/listProperties/property/item", ListItemConfig.class);
		d.addSetProperties("config/listProperties/property/item");
		d.addSetNext("config/listProperties/property/item", "addItemConfig");
		d.addSetNext("config/listProperties/property", "addListPropertyConfig");
		//
		d.addObjectCreate("config/rowProperties/property", RowPropertyConfig.class);
		d.addSetProperties("config/rowProperties/property");
		d.addSetNext("config/rowProperties/property", "addRowPropertyConfig");
		//
		ExcelBuilderConfig cfg = (ExcelBuilderConfig) d.parse(is);
		return cfg;
	}
	
}
