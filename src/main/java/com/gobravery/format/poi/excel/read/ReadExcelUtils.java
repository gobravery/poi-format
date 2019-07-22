package com.gobravery.format.poi.excel.read;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.gobravery.format.poi.excel.CellType;
import com.gobravery.format.poi.excel.ExcelBuilderConfig;
import com.gobravery.format.poi.excel.ListItemConfig;
import com.gobravery.format.poi.excel.ListPropertyConfig;

public class ReadExcelUtils {
	/**
	 * @param tpl  excel文档
	 * @param sheetIndex  表格编号0开始
	 * @param cfg  配置
	 * @param cb  回调
	 * @return
	 */
	public static List<Map<String,Object>> read(HSSFWorkbook tpl, int sheetIndex, ExcelBuilderConfig cfg, ReadCallback cb){
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		//
		HSSFSheet ws = tpl.getSheetAt(sheetIndex);
		if(cb != null) cb.preBuild(ws);
		//
		int end=ws.getLastRowNum();
		//
		for(ListPropertyConfig config : cfg.getListPropertyConfig()) {
			int start=config.getTplRow();
			while(start<end){
				//
				HSSFRow row=ws.getRow(start);
				//
				Map<String,Object> vm=new HashMap<String,Object>();
				//
				for(ListItemConfig item:config.getItemConfig()){
					HSSFCell vals=row.getCell(Integer.valueOf(item.getCellIndex()));
					vm.put(item.getPropertyName(),toCellValue(vals,item.getCellType()));
				}
				//
				start++;
				result.add(vm);
			}
		}
		
		if(cb != null) cb.postBuild(result,ws);
		
		return result;
	}
	
	/**
	 * @param hSSFWorkbookPath  excel文档地址
	 * @param sheetIndex  表格编号0开始
	 * @param excelBuilderConfigPath  配置xml地址
	 * @param cb  回调
	 * @return
	 */
	public static List<Map<String,Object>> read(String hSSFWorkbookPath, int sheetIndex, String excelBuilderConfigPath, ReadCallback cb){
		try {
			InputStream is = new FileInputStream(excelBuilderConfigPath);//"src/main/resources/simple/EvaBidReport.xml"
			ExcelBuilderConfig cfg = ExcelBuilderConfig.parse(is);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelBuilderConfigPath));//"src/main/resources/simple/EvaBidReport.xls"
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			return read(wb,sheetIndex,cfg,cb);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Map<String,Object>>();
	}
	/**
	 * 表格编号默认为0 sheet
	 * @param hSSFWorkbookPath  excel文档地址
	 * @param excelBuilderConfigPath  配置xml地址
	 * @return
	 */
	public static List<Map<String,Object>> read(String hSSFWorkbookPath, String excelBuilderConfigPath){
		return read(hSSFWorkbookPath,0,excelBuilderConfigPath,null);
	}
	public static Object toCellValue(HSSFCell val, CellType cellType) {
		if(cellType.equals(CellType.String)) {
			return val.getStringCellValue();
		} else if(cellType.equals(CellType.Date)) {
			return val.getDateCellValue();
		} else {
			return val.getStringCellValue();
		}
	}
	
}
