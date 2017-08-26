package com.gobravery.format.poi.word;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.VerticalAlign;

public class XWPFRunStyle {
	private UnderlinePatterns  underline;
	private String color;
	private int fontSize;
	private String fontFamily;
	private boolean strike;
	private VerticalAlign subscript;
	private boolean bold;
	private boolean italic;
	public UnderlinePatterns getUnderline() {
		return underline;
	}
	public void setUnderline(UnderlinePatterns underline) {
		this.underline = underline;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public boolean isStrike() {
		return strike;
	}
	public void setStrike(boolean strike) {
		this.strike = strike;
	}
	public VerticalAlign getSubscript() {
		return subscript;
	}
	public void setSubscript(VerticalAlign subscript) {
		this.subscript = subscript;
	}
	public boolean isBold() {
		return bold;
	}
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	public boolean isItalic() {
		return italic;
	}
	public void setItalic(boolean italic) {
		this.italic = italic;
	}
	
}
