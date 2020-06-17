package com.gobravery.format.poi.common;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonData {
	private static Logger logger = LoggerFactory.getLogger(JsonData.class);
	public static final String basePath="/static/css/json/";
	/**
	 * @param p 默认读取/static/css/json/下的json文件    例:rck.json
	 * @return
	 */
	public static JSONObject getJSONObject(String p){
		try {
			// ClassPathResource resource = new ClassPathResource(basePath+p);
			File filePath = getFile(p);
     //读取文件
			String input = FileUtils.readFileToString(filePath, "UTF-8");
			//将读取的数据转换为JSONObject
			JSONObject jsonObject = JSONObject.parseObject(input);
			
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("无法读取"+p);
			return JSONObject.parseObject("{}");
		}
	}
	private static File getFile(String p) {
		File filePath =new File(JsonData.class.getResource(p).getFile());
		return filePath;
	}
	/**
	 * @param p 默认读取/static/css/json/下的json文件    例:rck.json
	 * @return
	 */
	public static JSONArray getJSONArray(String p){
		try {
			File filePath = getFile(p);
     //读取文件
			String input = FileUtils.readFileToString(filePath, "UTF-8");
			//将读取的数据转换为JSONObject
			JSONArray jsonObject = JSONArray.parseArray(input);
			
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("无法读取"+p);
			return JSONArray.parseArray("[]");
		}
	}
}
