package org.phoenix.telnet.telnetclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.OS;
import org.apache.commons.net.telnet.TelnetClient;

/**
 * Telnet客户端
 * @author mengfeiyang
 *
 */

public class NetTelnet {
	private TelnetClient telnet;
	private InputStreamReader in;
	private PrintStream out;
	private BufferedReader br;
	private String respCharset = "UTF-8";

	public NetTelnet(String ip, int port) {
		try {
			if(OS.isFamilyWindows())telnet = new TelnetClient("VT220");
			else telnet = new TelnetClient();
			telnet.connect(ip, port);
			in = new InputStreamReader(telnet.getInputStream(),respCharset);
			out = new PrintStream(telnet.getOutputStream());
		} catch (SocketException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	public NetTelnet(String ip, int port,String respCharset) {
		try {
			if(OS.isFamilyWindows())telnet = new TelnetClient("VT220");
			else telnet = new TelnetClient();
			this.respCharset = respCharset;
			telnet.connect(ip, port);
			in =  new InputStreamReader(telnet.getInputStream(),respCharset);
			out = new PrintStream(telnet.getOutputStream());
		} catch (SocketException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	/**
	 * 向服务端发送命令
	 * @param command 命令内容
	 * @param pattern 指定接收内容的标识符
	 * @return
	 */
   public String sendCommand(String command, String pattern) {
        try {
    		out.println(command);
    		out.flush();
    		return Executors.newCachedThreadPool().submit(new DataReader(pattern)).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            return "exception:"+e.getClass().getName()+","+e.getMessage();
        }
    }
   
   private class DataReader implements Callable<String>{
	   private String pattern;
	   public DataReader(String pattern){
		   this.pattern = pattern;
	   }
	@Override
	public String call() throws Exception {
		br = new BufferedReader(in);
		String st = "";
		while(!st.endsWith(pattern) && telnet.isConnected()){
			st += br.readLine();
		}
		return st;
	}
   }
   
	public void disconnect() {
		try {
			telnet.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		NetTelnet n = new NetTelnet("localhost",8890);
		//System.out.println(n.sendCommand("!showusers","]"));
		//System.out.println(n.sendCommand("!showorders","datas"));
		System.out.println(n.sendCommand("!scanfile a.txt qq 1", "success!"));;
		n.disconnect();
	}
}