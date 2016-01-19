package org.phoenix.telnet.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.launcher.CommandLauncherFactory;
import org.apache.commons.io.FileUtils;
import org.phoenix.telnet.action.FileScan;
import org.phoenix.telnet.action.FileWatchAction;
import org.phoenix.telnet.telnetclient.CommandExecutor;

import com.google.common.io.Files;

/**
 * 每个新加入的请求，都会产生一个新的服务线程
 * @author mengfeiyang
 *
 */
public class NormalService implements Runnable{
    private Socket s;
    private String userName;
    private PrintWriter pw;
    private boolean runFlag = true;
	public NormalService(Socket s ,PrintWriter pw,String userName) {
		this.s = s;
		this.pw = pw;
		this.userName = userName;
	}
	public PrintWriter getPw() {
		return pw;
	}
	public String userId(){
		return userName;
	}

	@Override
	public void run() {
		BufferedReader br = null;
		try{			
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw.println("------ Welcome ------");
			pw.println("Your name is:"+Thread.currentThread().getName());
			while(runFlag){	
				String msg = br.readLine().trim(); 
                if(msg.startsWith("!setname=")){
                	userName = msg.split("=")[1];               	
                    for(NormalService srv : TCPServer.getClientList()){
                    	srv.getPw().println(Thread.currentThread().getName()+" change name :"+userName); 
                    }
                    Thread.currentThread().setName(userName);
                } else if(msg.equals("!showusers")){
                    	pw.println("now online users:"+TCPServer.getUser(TCPServer.getClientList())); 
                } else if(msg.equals("!showorders")){
                	pw.println("  1.!setname - setdisplayname,format: !setname=your name");
                	pw.println("  2.!showusers - show all users");
                	pw.println("  3.!showorders - show all instruct");
                	pw.println("  4.!start - start program ex:!start iexplore.exe");
                	pw.println("  5.!shell - start shell command ex:!shell sh test.sh 111");
                	pw.println("  6.!export - export a file ,if filepath have block,use '^' instead,ex:!export e:\\test\\test.txt isappend(true/false) datas");
                	pw.println("  7.!file - return a file content,ex:!file test.txt UTF-8");
                	pw.println("  8.!runsh - return Linux or Windows command result,ex:!runsh svn info");
                	pw.println("  9.!addfilewatch - add a file watching dog,max:100,ex:!addfilewatch a.log");
                	pw.println("  10.!getwatchstring - return file watch string,ex:!getwatchstring a.log");
                	pw.println("  11.!stopfilewatch - stop a file watching dog,ex:!stopfilewatch a.log");
                	pw.println("  12.!scanfile - scanfile a file by keyword,ex:!scanfile a.log test 1,1:contains keyword,0:not contains");
                	pw.println("  13.exit - exit client,ex:exit");
                } else if(msg.startsWith("!start")){
                	CommandLauncherFactory.createVMLauncher().exec(CommandLine.parse("cmd.exe /c start " + msg.split(" ")[1]), null);
                	pw.println("INFO:start "+msg.split(" ")[1]+ " success!");
                } else if(msg.startsWith("!shell")){
                	Runtime.getRuntime().exec(msg.replace("!shell ", "").trim());
                	pw.println("INFO: "+msg.replace("!shell", "")+ " success!");
                } else if(msg.startsWith("!runsh")){
                	String r = CommandExecutor.executeLinuxOrWindowsCmd(msg.replace("!runsh ", "").trim());
                	pw.println("RESULT: \n"+r+ "\n success!");
                }else if(msg.startsWith("!export")){
                	try{
	                	String[] msgs = msg.split(" ");
	                	String filePath = msgs[1];
	                	if(filePath.contains("^"))filePath = filePath.replace("^", " ");
	                	File f = new File(filePath);
	                	if(!f.exists())Files.createParentDirs(f);
	                	String temp = msg;
	                	for(int i=0;i<3;i++){
	                		temp = temp.replace(msgs[i], ""); 
	                	}
	                	String[] datas = temp.trim().split(";");
	                	if(msgs[2].equals("true")){
	                		f.delete();
	                		f.createNewFile();
	                	}
	            		for(String data : datas){
	            			FileUtils.write(f, data+"\r\n" , true);
	            		}
	                	pw.println("INFO: "+msg.replace("!", "")+ " success!");
                	}catch(Exception e){
                		pw.println("INFO: "+e.getClass().getName()+":"+e.getLocalizedMessage()+ "!");
                	}
                }else if(msg.startsWith("!file")){
                	StringBuilder strBuilder = new StringBuilder();
                	String[] cmds = msg.replace("!file ", "").split(" ");
                	List<String> ls = FileUtils.readLines(new File(cmds[0]),cmds[1]);
                	for(String s : ls){
                		strBuilder.append(s+"\n");
                	}
                	pw.println("INFO: \n"+strBuilder.toString()+ "\n success!");
                } else if(msg.startsWith("!addfilewatch")){
                	String[] cmds = msg.split(" ");
                	FileWatchAction.getInstance().addMonitor(cmds[1]);
                	pw.println("INFO: \naddfilewatch "+cmds[1]+ "\n success!");
                } else if(msg.startsWith("!getwatchstring")){
                	String[] cmds = msg.split(" ");
                	String oos = FileWatchAction.getInstance().getMoniting(cmds[1]);
                	if(oos == null)oos = "none update!";
                	pw.println(cmds[1]+" "+oos+ " success!");
                } else if(msg.startsWith("!stopfilewatch")){
                	String[] cmds = msg.split(" ");
                	FileWatchAction.getInstance().stopMoniting(cmds[1]);
                	pw.println("INFO: \nstopfilewatch "+cmds[1]+ ",now size : "+FileWatchAction.getInstance().getMonitorLength()+"\n success!");
                }  else if(msg.startsWith("!scanfile")){
                	String[] cmds = msg.split(" ");
                	pw.println("INFO: \n"+FileScan.getLinesByString(cmds[1], cmds[2].replace("%20", " "), cmds[3])+"\n success!");
                } else if(msg.equals("")){
                	pw.println("WARN:Cannot enter a null character!"); 
                } else {
                    for(NormalService srv : TCPServer.getClientList()){
                    	if(srv.userId().equals(userName)){
                    		srv.getPw().println("You say :" + msg ); 
                    	} else {
                    		srv.getPw().println(Thread.currentThread().getName()+" say :" + msg ); 
                    	} 
                    }
                }
                 
                if (msg.equals("exit")) {                 	
                	TCPServer.getClientList().remove(this);
                    for(NormalService srv : TCPServer.getClientList()){
                    	srv.getPw().println(Thread.currentThread().getName()+" come out! Now Total : "+TCPServer.getClientList().size() +",Now users:"+TCPServer.getUser(TCPServer.getClientList())); 
                    }
                    runFlag = false;
                    pw.close();  
                    br.close(); 
                    break;  
                } 
			}
		} catch(Exception e){
            try {
    			s.close();
                pw.close(); 
				br.close();
				TCPServer.getClientList().remove(this);
			} catch (IOException e1) {
			} 
			//e.printStackTrace();
		}
	}

}
