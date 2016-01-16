package org.phoenix.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串及数字判断工具
 * 
 * @author mengfeiyang
 *
 */
public class BasePatternUtil {
	/**
	 * 判断给定的字符串是否全部是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {

		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断指定的字符串是否全部为英文，包括大小写。如：abcABC,返回结果为true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEn(String str) {

		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断指定的字符串是否全部是中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCHS(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		for (int i = 0; i < str.length(); i++) {
			String s = String.valueOf(str.charAt(i));
			System.out.println(s);
			Matcher m = p.matcher(s);
			if (!m.find()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断指定的字符串中是否包含中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isContainsCHS(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(BasePatternUtil.isCHS("\u8dd1"));
	}
}
