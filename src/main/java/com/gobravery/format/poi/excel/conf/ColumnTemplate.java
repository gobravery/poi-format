package com.gobravery.format.poi.excel.conf;

import java.io.OutputStream;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gobravery.format.poi.excel.ExcelUtils;
import com.gobravery.format.poi.template.AbstractTemplate;

public class ColumnTemplate extends AbstractTemplate{
	public static final String cloumn_index="cellIndex";
	public static final String cloumn_property="propertyName";
	public static final String cloumn_title="cellTitle";
	private static final String itemConfig="itemConfig";
	//标题所属数据项
	private static final String headsKey="headsKey";
	//标题所属数据项
	private static final String contentKey="contentKey";
	//标题配置项
	private JSONObject headsConfig = new JSONObject();
	//标题数据
	private JSONObject headsData = new JSONObject();
	//数据配置项
	private JSONObject contentConfig = new JSONObject();
	/**
	 * 添加列
	 * @param jo
	 */
	public void addColumn(JSONObject jo){
		if(!jo.containsKey(cloumn_title)){
			throw new RuntimeException("必须包含标题属性!>>>"+cloumn_title);
		}
		if(!jo.containsKey(cloumn_property)){
			throw new RuntimeException("必须包含JSONOBJECT取值KEY属性!>>>"+cloumn_property);
		}
		if(!jo.containsKey(cloumn_index)){
			throw new RuntimeException("必须包含对excel填充行index属性!>>>"+cloumn_index);
		}
		if(contentConfig.containsKey(itemConfig)){
			JSONArray ja = contentConfig.getJSONArray(itemConfig);
			ja.add(jo);
			contentConfig.put(itemConfig,ja);
		}else{
			JSONArray ja = new JSONArray();
			ja.add(jo);
			contentConfig.put(itemConfig,ja);
		}
		addHeads(jo);
	}
	/**
	 * @param index  列号
	 * @param property 对应数据属性
	 * @param title 标题名称
	 */
	public void addColumn(int index,String property,String title){
		JSONObject jo = new JSONObject();
		jo.put(cloumn_index, index);
		jo.put(cloumn_property, property);
		jo.put(cloumn_title, title);
		addColumn(jo);
	}
	/**
	 * 添加标题
	 * @param jo
	 */
	private void addHeads(JSONObject jo){
		if(headsConfig.containsKey(itemConfig)){
			JSONArray ja = headsConfig.getJSONArray(itemConfig);
			ja.add(jo);
			headsConfig.put(itemConfig,ja);
		}else{
			JSONArray ja = new JSONArray();
			ja.add(jo);
			headsConfig.put(itemConfig,ja);
		}
		// 需要添加表头数据
		headsData.put(jo.getString(cloumn_property), jo.getString(cloumn_title));
	}
	/**
	 * 构建所有配置信息
	 * @return
	 */
	private JSONObject buildConfig(){
		JSONArray listPropertyConfig = new JSONArray();
		JSONObject config = new JSONObject();
		// 表头设置
		headsConfig.put("tplRow", this.tplRow);
		headsConfig.put("propertyName", headsKey);
		headsConfig.put("rowAddType", this.rowAddType);
		listPropertyConfig.add(headsConfig);
		//
		contentConfig.put("tplRow", this.tplRow+1);
		contentConfig.put("propertyName", contentKey);
		contentConfig.put("rowAddType", this.rowAddType);
		listPropertyConfig.add(contentConfig);
		// 
		config.put("listPropertyConfig",listPropertyConfig);
		return config;
	}
	/**
	 * @param outPath
	 * @param xlsTpl
	 * @param data
	 * @throws Exception 
	 */
	public void export(String outPath,String xlsTpl,JSONArray dataArray) throws Exception{
		JSONObject data = buildFillData(dataArray);
		//
		ExcelUtils.export(buildConfig(), xlsTpl, outPath, data);
	}
	/**
	 * @param outPath
	 * @param xlsTpl
	 * @param dataArray
	 * @throws Exception
	 */
	public void export(OutputStream outPath,String xlsTpl,JSONArray dataArray)throws Exception{
		JSONObject data = buildFillData(dataArray);
		//
		ExcelUtils.export(buildConfig(), xlsTpl, outPath, data);
	}
	private JSONObject buildFillData(JSONArray dataArray) {
		JSONObject data = new JSONObject();
		//填充头信息
		JSONArray heads = new JSONArray();
		heads.add(headsData);
		data.put(headsKey, heads);
		data.put(contentKey, dataArray);
		return data;
	}
	
}
