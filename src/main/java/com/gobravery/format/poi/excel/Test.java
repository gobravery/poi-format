package com.gobravery.format.poi.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) throws Exception {
		InputStream is = new FileInputStream("D:\\EvaBidReport.xml");
		ExcelBuilderConfig cfg = ExcelBuilderConfig.parse(is);
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("D:\\EvaBidReport.xls"));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
//		JSONObject obj = new JSONObject();
//		obj.put("projectName", "测试文件");
//		JSONArray arr = new JSONArray();
//		for(int i = 0; i < 100; i++) {
//			JSONObject item = new JSONObject();
//			item.put("seq", new Double(i + 1));
//			item.put("name", "供应商" + i);
//			item.put("remark", "是");
//			arr.add(item);
//		}
//		obj.put("suppliers", arr);
//		JSONArray arr1 = new JSONArray();
//		for(int i = 0; i < 10; i++) {
//			JSONObject item = new JSONObject();
//			item.put("remark", "备注" + i);
//			arr1.add(item);
//		}
//		obj.put("remarks", arr1);
//		
//		ExcelUtils.export(wb, 0, cfg, obj, null);
//		wb.write(new FileOutputStream("D:\\test1.xls"));
		JSONObject obj = new JSONObject();
		obj.put("projectName", "测试文件");
		obj.put("productName", "测试文件");
		obj.put("bidType", "测试文件");
		obj.put("techScoPercent", "测试文件");
		obj.put("totalScoPercent", "测试文件");
		
		JSONArray arr = new JSONArray();
		for(int i = 0; i < 10; i++) {
			JSONObject item = new JSONObject();
			item.put("compName", "测试文件" + i);
			item.put("EvaBidLuPrice", "测试文件" + i);
			item.put("TechScore", "测试文件" + i);
			item.put("BusinessScore", "测试文件" + i);
			item.put("PriceScore", "测试文件" + i);
			item.put("TotalScore", "测试文件" + i);
			item.put("EvaBidresult", "测试文件" + i);
			item.put("ranking", "测试文件" + i);
			arr.add(item);
			
		}
		obj.put("suppliers", arr);
		JSONArray arr1 = new JSONArray();
		for(int i = 0; i < 10; i++) {
			JSONObject item = new JSONObject();
			item.put("compName", "测试文件" + i);
			item.put("EvaBidLuPrice", "测试文件" + i);
			item.put("TechScore", "测试文件" + i);
			item.put("BusinessScore", "测试文件" + i);
			item.put("EvaBidresult", "测试文件" + i);
			arr1.add(item);
			
		}
		obj.put("rootKvalue", arr1);
		ExcelUtils.export(wb, 0, cfg, obj, null);
		wb.write(new FileOutputStream("D:\\test2.xls"));
	}
}
