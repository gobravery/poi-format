package com.gobravery.format.svg;

public class SvgUtils {
	private static SvgTransfer svgt = new SvgTransfer();
	/**
	 * @param svgCode svg图表内容
	 * @return
	 */
	public static String trasPNG(String svgCode){
		return svgt.toPNG(svgCode);
	}
	public static void trasPNG(String svgCode,String toPath){
		svgt.toPNG(svgCode,toPath);
	}
}
