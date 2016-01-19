package org.phoenix.telnet.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 每个新加入的请求，都会产生一个新的服务线程.此方法启动后，会向接入的对象推送数据
 * @author mengfeiyang
 *
 */
public class MonitorService implements Runnable{
    private Socket s;
    private String fileName;
    private PrintWriter pw;
    private boolean runFlag = true;
	private Timer timer = new Timer();
	private long lastTimeFileSize = 0; 
	private RandomAccessFile randomFile;
	public MonitorService(Socket s ,PrintWriter pw) {
		this.s = s;
		this.pw = pw;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public PrintWriter getPw() {
		return pw;
	}

	@Override
	public void run() {
		BufferedReader br = null;
		try{			
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw.println("welcome!file monit command is:!monit filename,if exit use: exit.");
			while(runFlag){	
				String msg = br.readLine().trim(); 
                if(msg.startsWith("!monit")){
                	setFileName(msg.split(" ")[1]);
                	if(TCPServer.getMonitorMap().get(fileName) == null){
                		List<MonitorService> list = new ArrayList<MonitorService>();
                		list.add(this);
                		TCPServer.getMonitorMap().put(fileName, list);
                		File f = new File(fileName);
                		if(!f.exists())throw new Exception(fileName+" file not found!,exception exit");
                		randomFile = new RandomAccessFile(f,"rw");
                		lastTimeFileSize = randomFile.length();
                		timer.schedule(new TimerTask() {
                			@Override
                			public void run() {	
                				try{
	                				String monitorString = "";
	                				randomFile.seek(lastTimeFileSize);
	                				monitorString+=randomFile.readLine();
	                	            while(randomFile.readLine() != null) { 
	                	            	monitorString += randomFile.readLine();
	                	            }  
	                	            if(!monitorString.equals("") && !monitorString.equals("null")){
	                	            	String finalString = new String(monitorString.getBytes("ISO-8859-1"),"GBK");
	                	            	for(MonitorService ms : TCPServer.getMonitorMap().get(fileName)){
	                	            		ms.getPw().println(finalString);
	                	            	}
	                	            }
	                	            monitorString = "";
	                	            lastTimeFileSize = randomFile.length(); 
                				}catch(Exception e){}
                			}
                		}, 0, 1000);
                	} else {
                		TCPServer.getMonitorMap().get(fileName).add(this);
                	}
                } else if(msg.equals("exit")){
                	if(TCPServer.getMonitorMap().get(fileName) == null){
                		throw new Exception(fileName+",none user add monit this file,exception exit");
                	} else {
                		TCPServer.getMonitorMap().get(fileName).remove(this);
                		if(TCPServer.getMonitorMap().get(fileName).size()==0)throw new Exception(fileName+",none user monit this file,thread normal exit");
                	}
                	pw.println("INFO:accept user's exit command....");
                } else {
                	pw.println("WARN:not support!");
                }
			}
		} catch(Exception e){
            try {
            	pw.println("INFO:all user exit,reason:"+e.getMessage());
            	runFlag = false;
            	timer.cancel();
            	TCPServer.getMonitorMap().remove(fileName);
    			s.close();
                pw.close(); 
				br.close();
			} catch (IOException e1) {
				pw.println("WARN:not support!");
			} 
		}
	}

}
