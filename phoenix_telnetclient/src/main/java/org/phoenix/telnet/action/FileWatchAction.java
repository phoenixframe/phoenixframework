package org.phoenix.telnet.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 监控文件改动，并返回改动的内容
 * @author mengfeiyang
 *
 */
public class FileWatchAction {
	private static FileWatchAction fileWatch = new FileWatchAction();
	private HashMap<String,String> dataMap = new HashMap<String,String>();
	private HashMap<String,Timer> monitObj = new HashMap<String,Timer>();
	private FileWatchAction(){}
	
	public static FileWatchAction getInstance(){
		return fileWatch;
	}
	/**
	 * 获取改动的内容
	 * @param fileName
	 * @return
	 */
	public synchronized String getMoniting(String fileName){
		String r = dataMap.get(fileName);
		dataMap.put(fileName,"");
		return r;
	}
	/**
	 * 获取监控器数量
	 * @return
	 */
	public int getMonitorLength(){
		return monitObj.size();
	}
	
	/**
	 * 停止一个监控项目
	 * @param fileName
	 */
	public synchronized void stopMoniting(String fileName){
		monitObj.get(fileName).cancel();
		monitObj.remove(fileName);
		dataMap.remove(fileName);
	}
	
	/**
	 * 添加一个监控项目
	 * @param fileName
	 * @param encoding
	 */
	public synchronized void addMonitor(String fileName){
		if(monitObj.get(fileName) == null){
			WatchFileHandler watchDog = new WatchFileHandler(fileName);
			Timer timer = new Timer();
			timer.schedule(watchDog, 0, 500);
			monitObj.put(fileName, timer);
		}
	}
	
	class WatchFileHandler extends TimerTask {
		private String fileName;
		private long lastTimeFileSize = 0; 
		private RandomAccessFile randomFile;

		public WatchFileHandler(String fileName){
			this.fileName = fileName;
			try {
				randomFile = new RandomAccessFile(fileName,"rw");
				lastTimeFileSize = randomFile.length();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
            try {
            	String str = "";
				randomFile.seek(lastTimeFileSize);
	            str+=randomFile.readLine();
	            while(randomFile.readLine() != null) { 
	            	str += randomFile.readLine();
	            }  
	            if(!str.equals("") && !str.equals("null")){
	            	if(dataMap.get(fileName).length()>10000)dataMap.put(fileName,"");
	            	dataMap.put(fileName, dataMap.get(fileName)+"\n"+new String(str.getBytes("ISO-8859-1"),"GBK"));
	            	//System.out.println(new String(str.getBytes("ISO-8859-1"),"GBK"));
	            }
	            str = "";
	            lastTimeFileSize = randomFile.length(); 
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
	}
	
	public static void main(String[] args) {
		FileWatchAction.getInstance().addMonitor("F:/a.txt");
	}
}
