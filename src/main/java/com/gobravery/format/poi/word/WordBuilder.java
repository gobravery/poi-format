package com.gobravery.format.poi.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.gobravery.format.poi.excel.CellType;
import com.gobravery.format.poi.word.config.ValueConfig;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WordBuilder {
	private Map<String, SimplePropertyConfig> valueType = new HashMap<String, SimplePropertyConfig>();
	private Map<Integer, XWPFRunStyle> runType = new HashMap<Integer, XWPFRunStyle>();
	private Integer currows = 0;

	public void buildSimpleProperty(XWPFDocument doc, JSONObject params, List<SimplePropertyConfig> config) {

		for (SimplePropertyConfig c : config) {
			valueType.put(c.getPropertyName(), c);
		}
		replaceInPara(doc, params);
	}

	public void buildListProperty(XWPFDocument doc, JSONObject params, List<ListPropertyConfig> config) {
		List<XWPFTable> iterator = doc.getTables();
		XWPFTable table;
		for (ListPropertyConfig lc : config) {
			currows = 0;// 初始化
			String tableIndex = lc.getTable();
			table = iterator.get(Integer.valueOf(tableIndex));
			replaceInTable(table, params, lc);
		}
	}
	public void buildTableProperty(XWPFDocument doc, JSONObject params, List<TablePropertyConfig> config) {
		List<XWPFTable> iterator = doc.getTables();
		XWPFTable table;
		for (TablePropertyConfig lc : config) {
			currows = 0;// 初始化
			String tableIndex = lc.getTable();
			table = iterator.get(Integer.valueOf(tableIndex));
			replaceInTable(table, params, lc);
		}
	}
	/**
	 * 替换段落里面的变量
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 */
	private void replaceInPara(XWPFDocument document, JSONObject params) {
		Iterator<XWPFParagraph> iterator = document.getParagraphsIterator();
		XWPFParagraph para;
		while (iterator.hasNext()) {
			para = iterator.next();
			this.replaceInPara(para, params);
		}
	}

	/**
	 * 替换段落里面的变量
	 * 
	 * @param para
	 *            要替换的段落
	 * @param params
	 *            参数
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void replaceInPara(XWPFParagraph para, JSONObject params){
		List<XWPFRun> runs;
		Matcher matcher;
		if (this.matcher(para.getParagraphText()).find()) {
			runs = para.getRuns();
			for (int i = 0; i < runs.size(); i++) {
				XWPFRun run = runs.get(i);
				System.out.println(run);
				String runText = run.toString();
				matcher = this.matcher(runText);
				if (matcher.find()) {
					//替换段落中多个文档
					String key=null;
					while ((matcher = this.matcher(runText)).find()) {
						//
						key = matcher.group(1);
						//开始替换
						runText = matcher.replaceFirst(getValue(key, params));
					}
					//run.setText(runText);
					// 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
					// 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
					XWPFRun temp=para.insertNewRun(i);
					XWPFRunStyle dest=new XWPFRunStyle();
					copyStyle(dest,run);
					copyRun(dest,temp);
					//删除旧的
					para.removeRun(i+1);
					//设置值
					setValue(temp,runText,valueType.get(key));
				}
			}
		}
	}
	private void setValue(XWPFRun temp,String value,ValueConfig conf){
		if(isImage(conf)){
			//图片
			try {
				String path = value;
				String name = getFileName(value);
				temp.addPicture(new FileInputStream(path),XWPFDocument.PICTURE_TYPE_PNG,name,Units.toEMU(conf.getWidth()), Units.toEMU(conf.getHeight()));
			} catch (InvalidFormatException e) {
				
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}else{
			temp.setText(value);
		}
	}
	/**
	 * 替换表格里面的变量
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 */
	private void replaceInTable(XWPFTable table, JSONObject params, ListPropertyConfig config) {

		List<XWPFTableRow> rows = table.getRows();// 所有行
		List<XWPFTableCell> cells;
		// List<XWPFParagraph> paras;
		JSONArray listparams = params.getJSONArray(config.getPropertyName());
		currows = config.getRow();// 开始行
		XWPFTableRow row=null;// 上一行
		for (int i = 0; i < listparams.size(); i++) {
			JSONObject item = listparams.getJSONObject(i);
			//
			if (config.getRowNext() == RowNext.replace) {
				row = rows.get(currows);
			} else {
				if (currows == config.getRow()) {
					row = rows.get(currows);
				} else {
					XWPFTableRow temprow = table.insertNewTableRow(currows);
					copyPro(row,temprow);
					row=temprow;
				}
			}
			//
			cells = row.getTableCells();
			for (ListItemConfig ite : config.getItemConfig()) {
				XWPFTableCell cell = cells.get(ite.getCellIndex());
				//cell.setText(getValue(ite.getPropertyName(), item));
				setCellValue(cell,getValue(ite.getPropertyName(), item,ite),Integer.valueOf(ite.getCellIndex()),ite);
			}
			/*
			 * for (XWPFTableCell cell : cells) { paras = cell.getParagraphs();
			 * for (XWPFParagraph para : paras) { this.replaceInPara(para,
			 * params); } }
			 */
			currows++;// 当前处理行
		}
	}
	private void replaceInTable(XWPFTable table, JSONObject params, TablePropertyConfig config) {

		List<XWPFTableRow> rows = table.getRows();// 所有行
		List<XWPFTableCell> cells;
		// List<XWPFParagraph> paras;
		String value = getValue(config.getPropertyName(), params);
		int row = config.getRow();// 开始行
		int cell = config.getCell();// 开始行
		XWPFTableRow tableRow=table.getRow(row);
			//
		XWPFTableCell tableCell = tableRow.getCell(cell);
			//
		setCellValue(tableCell,value,config);
			
	}
	/**
	 * 正则匹配字符串
	 * 
	 * @param str
	 * @return
	 */
	private Matcher matcher(String str) {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}
	/**
	 * 判断是否图片
	 * @param key
	 * @return
	 */
	private boolean isImage(ValueConfig conf){
		if (conf.getCellType() == CellType.Image) {
			return true;
		}
		return false;
	}
	/**
	 * 得到文件名
	 * @param file
	 * @return
	 */
	private String getFileName(String file){
		File f = new File(file);
		return f.getName();
	}
	/**
	 * 参数类型
	 * @param key
	 * @param params
	 * @param config
	 * @return
	 */
	private String getValue(String key, JSONObject params,ListItemConfig config) {
		if (config.getCellType() == CellType.Date) {
			return WordUtils.sdf.format(params.opt(key));
		} else {
			return String.valueOf(params.opt(key));
		}
	}
	private String getValue(String key, JSONObject params) {
		if (valueType.get(key).getCellType() == CellType.Date) {
			return WordUtils.sdf.format(params.opt(key));
		} else {
			return String.valueOf(params.opt(key));
		}
	}

	private void copyPro(XWPFTableRow sourceRow, XWPFTableRow targetRow) {
		if(sourceRow==null){
			throw new RuntimeException("表格插入时，没找到原行");
		}
		// 复制行属性
		targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
		List<XWPFTableCell> cellList = sourceRow.getTableCells();
		if (null == cellList) {
			return;
		}
		// 添加列、复制列以及列中段落属性
		XWPFTableCell targetCell = null;
		for (XWPFTableCell sourceCell : cellList) {
			targetCell = targetRow.addNewTableCell();
			// 列属性
			targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
			// 段落属性
			targetCell.getParagraphs().get(0).getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());
			//targetCell.setParagraph(sourceCell.getParagraphs().get(0));
		}
	}
	private void copyRun(XWPFRunStyle src,XWPFRun opt){
		opt.setUnderline(src.getUnderline());
		opt.setColor(src.getColor());
		if(src.getFontSize()>0){
			opt.setFontSize(src.getFontSize());
			//System.out.println(src.getFontSize()+"size");
		}
		opt.setFontFamily(src.getFontFamily());
		opt.setStrikeThrough(src.isStrike());
		opt.setSubscript(src.getSubscript());
		opt.setBold(src.isBold());
		opt.setItalic(src.isItalic());
	}
	private void copyStyle(XWPFRunStyle opt,XWPFRun src){
		try {
			BeanUtils.copyProperties(opt, src);
			opt.setUnderline(src.getUnderline());
			opt.setSubscript(src.getSubscript());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private XWPFParagraph findPar(List<XWPFParagraph> ghs){
		for(XWPFParagraph g:ghs){
			if(!StringUtils.isEmpty(g.getParagraphText())){
				return g;
			}
		}
		return null;
	}
	private XWPFRun findRun(List<XWPFRun> ghs){
		for(XWPFRun r:ghs){
			if(!StringUtils.isEmpty(r.toString())){
				return r;
			}
		}
		return null;
	}
	private void setCellValue(XWPFTableCell cell,String value,TablePropertyConfig conf){
		setCellValue(cell,value,-1,conf);
	}
	private void setCellValue(XWPFTableCell cell,String value,Integer index,ValueConfig conf){
		//
		XWPFRunStyle src=new XWPFRunStyle();
		//只保留一个
		XWPFParagraph gh=findPar(cell.getParagraphs());
		if(gh==null){
			gh=cell.getParagraphs().get(0);
		}
		XWPFRun run=findRun(gh.getRuns());
		//
		if(run!=null){
			//第一次取样列的值
			copyStyle(src,run);
			runType.put(index,src);
		}else{
			//之后取第一次样列的值
			if(index<0){
				//为-1表示直接取原cell的样式
				copyStyle(src,gh.getRuns().get(0));
			}else{
				src=runType.get(index);
			}
		}
		//
		XWPFRun temp=gh.insertNewRun(0);
		
		
		copyRun(src,temp);
		if(gh.getRuns().size()>1){
			gh.removeRun(1);
		}
		//temp.setText(value);
		setValue(temp,value,conf);
	}
}
