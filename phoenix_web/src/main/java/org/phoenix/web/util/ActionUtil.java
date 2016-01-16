package org.phoenix.web.util;

import java.util.Properties;

import com.opensymphony.xwork2.ActionContext;

public class ActionUtil {
	public final static String REDIRECT = "redirect";
	public final static String SUCCESS = "success";
	
	public static boolean isEmpty(String str) {
		if(str==null||"".equals(str.trim())) {
			return true;
		}
		return false;
	}
	
	public static void setUrl(String url) {
		ActionContext.getContext().put("url", url);
	}
	
	/*
	 * 获取用户可以访问的功能
	 */
	public static String[] getUserAuth() {
		Properties prop = PropertiesUtil.getAuthProp();
		String user = prop.getProperty("user");
		return user.split(",");
	}
	
	/*
	 * 获取用户不能访问的功能
	 */
	public static String[] getUserNotAuth() {
		Properties prop = PropertiesUtil.getAuthProp();
		return (prop.getProperty("admin")).split(",");
	}
	
	public static boolean checkUrl(String action) {
		for(String url:getUserAuth()) {
			if(action.startsWith(url)) {
				return true;
			}
		}
		for(String url:getUserNotAuth()) {
			if(action.startsWith(url)) {
				return false;
			}
		}
		return true;
	}
	
}
