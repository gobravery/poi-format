package com.gobravery.format.poi.word;

import java.text.SimpleDateFormat;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import net.sf.json.JSONObject;

public class WordUtils {
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void export(XWPFDocument tpl, WordBuilderConfig cfg, JSONObject obj, WordBuilderCallback cb) {
		WordBuilder wb=new WordBuilder();
		wb.buildSimpleProperty(tpl, obj, cfg.getSimplePropertyConfig());
		//
		wb.buildListProperty(tpl, obj, cfg.getListPropertyConfig());
		//
		if(cb != null) cb.postBuild(tpl);
	}
}
