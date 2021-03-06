package com.gobravery.format.poi.word;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) throws Exception {
		InputStream is = new FileInputStream("src/main/resources/simple/word/test.xml");
		//
		WordBuilderConfig cfg = WordBuilderConfig.parse(is);
		//
		FileInputStream fs = new FileInputStream("src/main/resources/simple/word/test.docx");
		XWPFDocument  wb = new XWPFDocument (fs);

		JSONObject obj = new JSONObject();
		obj.put("head", "测试文件");
		obj.put("xm", "张三");
		
		JSONArray arr = new JSONArray();
		for(int i = 0; i < 10; i++) {
			JSONObject item = new JSONObject();
			item.put("inOrder", "2" + i);
			item.put("Name", "测试文件" + i);
			item.put("CardType", "测试文件" + i);
			item.put("CardNum", "测试文件" + i);
			item.put("Remark", "测试文件" + i);
			arr.add(item);
		}
		obj.put("suppliers", arr);
		
		WordUtils.export(wb, cfg, obj, null);
		wb.write(new FileOutputStream("src/main/resources/simple/word/test_res.docx"));
	}
}
