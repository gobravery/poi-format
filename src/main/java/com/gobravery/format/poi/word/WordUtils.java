package com.gobravery.format.poi.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.alibaba.fastjson.JSONObject;

/**  

* <p>Title: WordUtils</p>  

* <p>Description: 用于doc文件导出功能</p>  

* @author qinming  

* @date 2019年12月11日  

*/  
public class WordUtils {
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * @param tpl 模版文件
	 * @param cfg 配置文件
	 * @param obj 填充数据
	 * @param cb  调用
	 */
	public static void export(XWPFDocument tpl, WordBuilderConfig cfg, JSONObject obj, WordBuilderCallback cb) {
		WordBuilder wb=new WordBuilder();
		wb.buildSimpleProperty(tpl, obj, cfg.getSimplePropertyConfig());
		//
		wb.buildTableProperty(tpl, obj, cfg.getTablePropertyConfig());
		//
		wb.buildListProperty(tpl, obj, cfg.getListPropertyConfig());
		//
		if(cb != null) cb.postBuild(tpl);
	}
	/**
	 * @param config 配置文件xml格式路径
	 * @param wordtpl doc模版文件docx格式路径
	 * @param item 填充数据
	 * @param tofile 填充好数据的文件路径
	 * @return 
	 * @throws Exception
	 */
	public static void export(String config,String wordtpl,JSONObject item,String tofile) throws Exception {
		InputStream is = new FileInputStream(config);
		//
		WordBuilderConfig cfg = WordBuilderConfig.parse(is);
		//
		FileInputStream fs = new FileInputStream(wordtpl);
		XWPFDocument  wb = new XWPFDocument (fs);
		//
		export(wb, cfg, item, null);
		//
		wb.write(new FileOutputStream(tofile));
		//
		return;
	}
	/**
	 * @param config 配置文件xml格式路径
	 * @param wordtpl doc模版文件docx格式路径
	 * @param item 填充数据
	 * @return 返回填充好的文件路径
	 * @throws Exception
	 */
	public static String export(String config,String wordtpl,JSONObject item) throws Exception {
		//文件名
		String filename = UUID.randomUUID().toString()+".docx";
		//临时文件地址
		String file = getTempFile()+"/"+filename;
		//
		export(config,wordtpl,item,file);
		return file;
	}
	public static String getTempFile(){
		String sysos = System.getProperties().getProperty("os.name");
		String file = "/var/temp/doc";
		if(sysos.toLowerCase().startsWith("win")){ 
			file = "/temp/doc";
		}
		File fs = new File(file);
		if(!fs.exists()){
			//创建文件目录
			fs.mkdirs();
		}
		return file;
	}
}
