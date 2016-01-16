package org.phoenix.aop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * log4j平台日志类，写入日志信息到指定log
 * 
 * @author mengfeiyang
 * 
 */
public class PhoenixLogger {

	static Logger logger = Logger.getLogger(PhoenixLogger.class);

	/**
	 * 初始化logger类
	 * PhoenixLogger.class.getResource("/").getPath().replace("%20", " ")+
	 * System.getProperty("user.dir")+\\src\\phoenix\\config\\logger.properties
	 */
	public PhoenixLogger() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(PhoenixLogger.class.getResource("/").getPath().replace("%20", " ")+"log4j.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 PropertyConfigurator.configure(properties);
	}

	public static void debug(String debugMessage) {
		logger.debug(debugMessage);		
	}

	public static void info(String infoMessage) {
		logger.info(infoMessage);
	}

	public static void warn(String warnMessage) {
		logger.warn(warnMessage);
	}

	public static void error(String errorMessage) {
		logger.error(errorMessage);
	}
	
	public static void exportLog(String logfilepath) {
		File test = new File(logfilepath);
		PrintStream printStream = null;
		try {
			printStream = new PrintStream(new FileOutputStream(test));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintStream out = printStream;
		System.setOut(out);
	}
}