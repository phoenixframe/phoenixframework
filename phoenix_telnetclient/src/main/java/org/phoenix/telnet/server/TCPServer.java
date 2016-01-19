package org.phoenix.telnet.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TCP聊天程序的客户端，负责为接入的会话生成独立线程。
 * 客户端telnet连接方式：telnet 127.0.0.1 8889
 * @author mengfeiyang
 *
 */
public class TCPServer {
	private static List<NormalService> clientList = new ArrayList<NormalService>();
	private static ConcurrentHashMap<String,List<MonitorService>> monitorMap = new ConcurrentHashMap<String,List<MonitorService>>();
	public static synchronized List<NormalService> getClientList(){
		return clientList;
	}
	public static ConcurrentHashMap<String,List<MonitorService>> getMonitorMap(){
		return monitorMap;
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		
		ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
		int count = 0;
		while(true){
			count++;
			Socket s = ss.accept();
			String userId = "User" + count;
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream(),"UTF-8"),true);
			if(args.length >= 2 && args[1].equals("monitor")){
				MonitorService monitorService = new MonitorService(s,pw);
				new Thread(monitorService).start();
			} else {
				NormalService normalService = new NormalService(s,pw,userId);
				getClientList().add(normalService);
				new Thread(normalService,userId).start();
				sayToAll(userId);
			}
		}
	}
	
	public static synchronized void sayToAll(String name){
		for(NormalService sv : clientList){
			sv.getPw().println(name +" is come in! Now Total:" + clientList.size()+",Now Users:"+getUser(clientList));
		}
	}
	
	public static synchronized String getUser(List<NormalService> clientList){
		String users = "";
		for(NormalService s : clientList){
			users += s.userId()+",";
		}
		return "[ "+users.substring(0, users.length()-1)+" ]";
	}
}
