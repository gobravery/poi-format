package com.gobravery.format.poi.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface WordBuilderCallback {
	void preBuild(XWPFDocument  ws);
	void postBuild(XWPFDocument  ws);
}
