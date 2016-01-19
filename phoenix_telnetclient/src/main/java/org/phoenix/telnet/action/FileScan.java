package org.phoenix.telnet.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * 扫描文件，并根据关键词进行过滤
 * @author mengfeiyang
 *
 */
public class FileScan {
	/**
	 * 执行文件扫描，返回条目的list
	 * @param fileName
	 * @param keyword 
	 * @param rule 0表示会保留不包含关键词的条目，1表示会保留包含关键词的条目
	 * @return
	 */
	public synchronized static List<String> getLinesByList(String fileName,String keyword,String rule){
		List<String> klist = new ArrayList<String>();
		try {
			List<String> lines = FileUtils.readLines(new File(fileName));
				if(rule.equals("0")){
					for(String line : lines){
						if(!line.contains(keyword)){
							klist.add(line);
						}
					}
				} else if(rule.equals("1")){
					for(String line : lines){
						if(line.contains(keyword)){
							klist.add(line);
						}
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return klist;
	}
	
	/**
	 * 执行文件扫描，返回string
	 * @param fileName
	 * @param keyword 
	 * @param rule 0表示会保留不包含关键词的条目，1表示会保留包含关键词的条目
	 * @return
	 */
	public synchronized static String getLinesByString(String fileName,String keyword,String rule){
		return Arrays.toString(getLinesByList(fileName,keyword,rule).toArray());
	}
}
