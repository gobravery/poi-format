package com.gobravery.format.poi.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**  

* <p>Title: ExcelUtils</p>  

* <p>Description: 用于excel文档导出</p>  

* @author qinming  

* @date 2019年12月19日  

*/  
public class ExcelUtils {
	/**
	 * @param xmlTpl 模版配置
	 * @param excelTpl  模版文件
	 * @param outPath 输出文件
	 * @param data 填充数据
	 * @return
	 * @throws Exception
	 */
	public static String export(String xmlTpl,String excelTpl,String outPath,JSONObject data)throws Exception{
		OutputStream ofs = new FileOutputStream(outPath);
		export(xmlTpl,excelTpl,ofs,data);
		return outPath;
	}
	/**
	 * @param xmlTpl 模版配置
	 * @param excelTpl  模版文件
	 * @param outPath 输出流
	 * @param data 填充数据
	 * @throws Exception
	 */
	public static void export(String xmlTpl,String excelTpl,OutputStream outPath,JSONObject data)throws Exception{
		ExcelProcess ep =new ExcelProcess();
		InputStream is = new FileInputStream(xmlTpl);
		ExcelBuilderConfig cfg = ExcelBuilderConfig.parse(is);
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelTpl));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		ep.export(wb, 0, cfg, data, null);
		wb.write(outPath);
		return;
	}
	/**
	 * @param xmlTpl 模版配置
	 * @param excelTpl  模版文件
	 * @param outPath 输出文件
	 * @param data 填充数据
	 * @return
	 * @throws Exception
	 */
	public static String export(JSONObject xmlTpl,String excelTpl,String outPath,JSONObject data)throws Exception{
		OutputStream ofs = new FileOutputStream(outPath);
		export(xmlTpl,excelTpl,ofs,data);
		return outPath;
	}
	/**
	 * @param xmlTpl 模版配置改用json格式
	 * @param excelTpl  模版文件
	 * @param outPath 输出流
	 * @param data 填充数据
	 * @throws Exception
	 */
	public static void export(JSONObject xmlTpl,String excelTpl,OutputStream outPath,JSONObject data)throws Exception{
		ExcelProcess ep =new ExcelProcess();
		// InputStream is = new FileInputStream(xmlTpl);
		ExcelBuilderConfig cfg = JSON.toJavaObject(xmlTpl, ExcelBuilderConfig.class);
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelTpl));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		ep.export(wb, 0, cfg, data, null);
		wb.write(outPath);
		return;
	}
}
