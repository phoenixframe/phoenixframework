package org.phoenix.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 正则匹配校验
 * @author mengfeiyang
 *
 */
public class MethodPattern {
	
	public static String result(String content,String rule){
        Pattern p=Pattern.compile(rule);
        Matcher m=p.matcher(content);
        while(m.find()){
        	return m.group(1);
        }
		return null;
	}
}
