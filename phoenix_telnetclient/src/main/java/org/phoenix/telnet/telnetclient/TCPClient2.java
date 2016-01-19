package org.phoenix.telnet.telnetclient;

import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;  
import java.net.Socket;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
 
public class TCPClient2{  
    private static final int PORT = 8801;  
    private static ExecutorService exec = Executors.newCachedThreadPool();  
  
    public static void main(String[] args) throws Exception{    
        new TCPClient2();  
    }  
  
    public TCPClient2(){  
        try{  
            Socket socket = new Socket("localhost",PORT);  
            exec.execute(new Sender(socket));  
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
            String msg;  
            while((msg = br.readLine())!=null){  
                System.out.println(msg);  
            }  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
   
    static class Sender implements Runnable{  
        private Socket socket;  
          
        public Sender(Socket socket){  
            this.socket = socket;  
        }  
  
        public void run(){  
            try{  
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);  
                while(true){  
                    String msg = br.readLine();  
                    pw.println(msg);  
                    if (msg.trim().equals("exit")) {  
                        pw.close();  
                        br.close();  
                        exec.shutdownNow();  
                        break;  
                    }  
                }  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
        }  
    }  
}  
