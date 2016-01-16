package org.phoenix.node.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 获取当前时间，并以各种形式返回。<br>
 * 并且可以对时间差进行计算<br>
 * @author mengfeiyang
 *
 */

public class GetNow {
	
	/**
	 * 返回默认的当前的时间。返回格式为：yyyy-MM-dd HH:mm:ss<br>
	 * @author mengfeiyang
	 * @return
	 */	
	public static String getCurrentTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE); 
	
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		//sdf.applyPattern("HH:mm:ss,SSS");
		Date currDate = new Date();
		String timeStr = sdf.format(currDate); 
				
		return timeStr;
	}
	
	/**
	 * 将当前获得的毫秒数转换成指定格式的时间.<br>
	 * 如："yyyy-MM-dd HH:mm:ss"<br>
	 * @author mengfeiyang
	 * @param PATTERN
	 * @return
	 */	
	public static String getCurrentTime(String PATTERN){
		SimpleDateFormat sdf = new SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE); 		
		sdf.applyPattern(PATTERN);
		Date currDate = new Date();
		String timeStr = sdf.format(currDate); 
				
		return timeStr;
	}
	
	public static String getFormatTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		return sdf.format(date);
	}
	
	/**
	 * 将指定的毫秒数转换成指定格式的时间.<br>
	 * 如："yyyy-MM-dd HH:mm:ss"<br>
	 * @author mengfeiyang
	 * @param PATTERN
	 * @return
	 */	
	public static String getCurrentTime(long time,String PATTERN){
		SimpleDateFormat sdf = new SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE); 		
		sdf.applyPattern(PATTERN);
		String timeStr = sdf.format(time); 
				
		return timeStr;
	}

}
