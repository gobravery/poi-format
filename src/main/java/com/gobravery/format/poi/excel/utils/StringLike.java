package com.gobravery.format.poi.excel.utils;

import java.util.ArrayList;
import java.util.List;

/**  

* <p>Title: StringLike</p>  

* <p>Description: </p>  

* @author qinming  

* @date 2019年5月31日  

*/  
public class StringLike {
	public void trim(String[] arg){
		for(int i=0;i<arg.length;i++){
			arg[i]=arg[i].trim();
		}
	}
	private void log(String[] arg){
		for(String i:arg){
			System.out.print(i+" ");
		}
		System.out.println();
	}
	/**
	 * @param a 
	 * @param b
	 * @param condition 匹配表达式，同sql中的like如：%_、_%_等
	 * @return
	 */
	public boolean like(String a, String b, String condition) {
		String[] cond = condition.split("%");
		trim(cond);
		//log(cond);
		if (other(cond)) {
			return otherLike(a, b, cond);
		}
		if (condition.startsWith("_") && condition.endsWith("_")) {
			// _%开头
			return start_end_(a, b, cond);
		}
		if (condition.startsWith("_") && condition.endsWith("%")) {
			// %开头
			return start_end(a, b, cond);
		}
		if (condition.startsWith("%") && condition.endsWith("_")) {
			// %_结尾
			return startEnd_(a, b, cond);
		}
		if (condition.startsWith("%") && condition.endsWith("%")) {
			// %结尾
			return startEnd(a, b, cond);
		}
		return false;
	}

	/**
	 * 匹配:_% 开头 %_ 结尾
	 * 
	 */
	private boolean start_end_(String a, String b, String[] condition) {
		String opt = a;
		String match = b;
		if (a.length() > b.length()) {
			opt = b;
			match = a;
		}
		if (condition.length >= 2) {
			// 开头
			if (opt.length() < condition[0].length()) {
				return false;
			}
			String startStr = opt.substring(0, condition[0].length());
			opt = opt.substring(condition[0].length());
			// 结尾
			if (opt.length() < condition[condition.length-1].length()) {
				return false;
			}
			String endStr = opt.substring(opt.length() - condition[condition.length-1].length());
			opt = opt.substring(0, opt.length() - condition[condition.length-1].length());
			//
			if (match.startsWith(startStr) && match.endsWith(endStr)) {
				if (condition.length > 2) {

					return midleLike(opt, match, condition, 1, 1);
				}
				return true;
			}
		}
		return false;
	}

	private boolean other(String[] condition) {
		if (condition == null || condition.length == 0) {
			return true;
		}
		if (condition.length == 1 && condition[0].length() > 0) {
			return true;
		}

		return false;
	}

	private boolean otherLike(String a, String b, String[] condition) {
		if (condition.length == 1 && condition[0].length() > 0) {
			String start = a.substring(0, condition[0].length());
			return b.startsWith(start);
		} else {
			return fullLike(a, b);
		}
	}

	private boolean midleLike(String opt, String match, String[] condition, int start, int end) {
		String[] mycod = new String[condition.length - (start + end)];
		int j=0;
		for (int i = start; i < condition.length - end; i++) {
			mycod[j] = condition[i];
			j++;
		}
		//
		return startEnd(opt, match, mycod);
	}

	public static void main(String[] args) {
		StringLike sl=new StringLike();
		System.out.println(sl.like("我的一空有一一空有一品山","我一一空水山","_%__%_%_"));
		String[] sd = "%_%_%_%".split("%");
		String h="hhhh";
		String d=h;
		h="h";
		System.out.println("d>>>"+d+",h>>"+h);
		System.out.println(sd.length + ">" + sd[0] + ">" + sd[sd.length - 1]);
	}

	/**
	 * 匹配:_% 开头 _% 结尾
	 * 
	 */
	private boolean start_end(String a, String b, String[] condition) {
		String opt = a;
		String match = b;
		if (a.length() > b.length()) {
			opt = b;
			match = a;
		}
		if (condition.length >= 2) {
			// 开头
			if (opt.length() < condition[0].length()) {
				return false;
			}
			String startStr = opt.substring(0, condition[0].length());
			opt = opt.substring(condition[0].length());
			//
			if (match.startsWith(startStr)) {
				if (condition.length > 2) {

					return midleLike(opt, match, condition, 1, 0);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 匹配:%_ 开头 %_ 结尾
	 * 
	 */
	private boolean startEnd_(String a, String b, String[] condition) {
		String opt = a;
		String match = b;
		if (a.length() > b.length()) {
			opt = b;
			match = a;
		}
		//
		if (condition.length >= 2) {

			// 结尾
			if (opt.length() < condition[condition.length-1].length()) {
				return false;
			}
			String endStr = opt.substring(opt.length() - condition[condition.length-1].length());
			opt = opt.substring(0, opt.length() - condition[condition.length-1].length());
			//
			if (match.endsWith(endStr)) {
				if (condition.length > 2) {

					return midleLike(opt, match, condition, 0, 1);
				}
				return true;
			}
		}
		//
		return false;
	}

	/**
	 * 匹配:%_ 开头 _% 结尾
	 * 
	 */
	private boolean startEnd(String a, String b, String[] condition) {
		String opt = a;
		String match = b;
		if (a.length() > b.length()) {
			opt = b;
			match = a;
		}
		// opt 拆解
		int total = getTotalLen(condition);
		if(total>opt.length()){
			return false;
		}
		//
		List<String[]> res=new ArrayList<String[]>();
		//分拆
		dep(0,opt,condition,res);
		//for(String[] s:res){
		//	log(s);
		//}
		//开始匹配
		boolean flag=true;
		for(String[] r:res){
			String tm=new String(match);
			for(String i:r){
				if(tm.indexOf(i) < 0){
					flag=false;
					break;
				}
				tm=tm.substring(tm.indexOf(i));
			}
			if(flag){
				return true;
			}else{
				flag=true;
			}
		}
		//
		return false;
	}

	private int getTotalLen(String[] condition) {
		int total=0;
		for(int i=0;i<condition.length;i++){
			total+=condition[i].length();
		}
		return total;
	}
	private int getTotalLen(String[] condition,int cp) {
		int total=0;
		for(int i=0;i<cp;i++){
			total+=condition[i].length();
		}
		return total;
	}
	/**
	 * cp 偏移量 
	 * OPT 等分解的字符串
	 * cond 分解的表达式
	 * res 分解结果
	 */
	private void dep(int cp,String opt,String[] cond,List<String[]> res){
		if(cp>=cond.length)return;
		
		//需获取字符串总长度
		int total = getTotalLen(cond);
		//偏移的起始长度
		int cpTotal = getTotalLen(cond,cp);
		//可偏移总长度
		int countp=opt.length()-total;
		//当前偏移长度
		int up=0;
		while(up<=countp){
			//每次偏移一个字符
			String top=opt.substring(0, cpTotal)+opt.substring(cpTotal+up);
			String[] r=new String[cond.length];
			int i=0;
			for(String c:cond){
				if(c.length()>0){
					r[i] = top.substring(0,c.length());
					top=top.substring(c.length());
				}
				i++;
			}
			res.add(r);
			up++;
		}
		//System.out.println(cp+","+opt+",countp"+countp);
		//如可以偏移
		if(countp>0){
			dep(++cp,opt,cond,res);
		}
	}
	private boolean fullLike(String e, String b) {
		if (e == null || b == null || empty(e) || empty(b)) {
			return false;
		}
		return e.trim().equals(b.trim());
	}

	public static boolean empty(String v) {
		return v == null || v.equals("");
	}
}
