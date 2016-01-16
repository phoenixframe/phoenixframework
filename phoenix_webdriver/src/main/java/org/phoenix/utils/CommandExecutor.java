package org.phoenix.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.exec.OS;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

/**
 * 命令行执行工具,默认编码：UTF-8，可通过setEncoding方法来设置编码格式。<br>
 * 如：GBK,GB2312等.
 * @author mengfeiyang
 *
 */
public class CommandExecutor {
	private static String encoding = "UTF-8";
	
	public static String getEncoding() {
		return encoding;
	}
	public static void setEncoding(String encoding) {
		CommandExecutor.encoding = encoding;
		
	}
	/**
	 * 普通方法，需要自己构造命令.执行完成后以String形式返回执行结果
	 * @param commands
	 * @return
	 */
	public static String execute(String[] commands){
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(commands);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getProcessResult(process);
	}
	/**
	 * 执行Windows命令，执行完成后以String形式返回执行结果
	 * @param command
	 * @return
	 */
	public static String executeWindowsCmd(String command){
		String[] cmds = {"cmd","/c",command};
		return execute(cmds);
	}
	/**
	 * 执行Linux命令，执行完成后以String形式返回执行结果
	 * @param command
	 * @return
	 */
	public static String executeLinuxCmd(String command){
		String[] cmds = {"/bin/sh","-c",command};
		return execute(cmds);
	}
	/**
	 * 自动根据操作系统的类型执行对应的命令类型。执行完成后以String形式返回执行结果<br>
	 * 如命令："svn info ...",即可以在windows下执行也可以在Linux下执行<br>
	 * 使用此命令时只需关注命令内容，而无需再判断操作系统适配<br>
	 * @param command
	 * @return
	 */
	public static String executeLinuxOrWindowsCmd(String command){
		if(OS.isFamilyWindows()){
			return executeWindowsCmd(command);
		} else if(OS.isFamilyUnix()){
			return executeLinuxCmd(command);
		} else {
			return "仅支持Linux和Windows命令";
		}
	}
	
	private static String getProcessResult(Process process){
		StringBuilder stringBuilder = new StringBuilder();
		try{
			stringBuilder.append("-------start\r\n");
			InputStream inStream = process.getInputStream();
			LineIterator li = IOUtils.lineIterator(inStream,encoding);
			while(li.hasNext()){
				String line = li.next();
				System.out.println(line);
				stringBuilder.append(line+"\r\n");
			}
			stringBuilder.append("-------end\r\n");
		}catch(Exception e){
			e.printStackTrace();
			stringBuilder.append(e.getClass().getName()+","+e.getMessage());
		}
		return stringBuilder.toString();
	}
}
