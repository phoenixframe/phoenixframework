package org.phoenix.utils;

import java.util.Properties;

public class SystemInfo {
	public static int getArch(){
		if(System.getProperty("os.arch").contains("64")){
			return 64;
		}else{
			return 32;
		}
	}
	
	public static boolean isWindows(){
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			return true;
		}
		return false;
	}
	
	public static Properties getSystemProperties(){
		System.getProperties().list(System.out);
		return System.getProperties();
	}
	
	public static void main(String[] args) {
		System.out.println(SystemInfo.isWindows());
	}
}
