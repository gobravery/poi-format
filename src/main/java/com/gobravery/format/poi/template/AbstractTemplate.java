package com.gobravery.format.poi.template;

/**  

* <p>Title: AbstractTemplate</p>  

* <p>Description: 模版基础配置</p>  

* @author qinming  

* @date 2020年6月17日  

*/  
public class AbstractTemplate {
	/**
	 * 
	 */
	protected int tplRow=0;
	protected String propertyName;
	protected int rowAddType=0;
	
	public int getTplRow() {
		return tplRow;
	}
	/**
	 * 开始行
	 * @param tplRow
	 */
	public void setTplRow(int tplRow) {
		this.tplRow = tplRow;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public int getRowAddType() {
		return rowAddType;
	}
	public void setRowAddType(int rowAddType) {
		this.rowAddType = rowAddType;
	}
	
}
