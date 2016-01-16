package org.phoenix.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;

import org.apache.commons.exec.OS;
import org.apache.commons.net.telnet.TelnetClient;

/**
 * Telnet客户端
 * @author mengfeiyang
 *
 */

public class NetTelnet {
	private TelnetClient telnet;
	private InputStream in;
	private PrintStream out;

	public NetTelnet(String ip, int port) {
		try {
			if(OS.isFamilyWindows())telnet = new TelnetClient("VT220");
			else telnet = new TelnetClient();
			telnet.connect(ip, port);
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
    		out.println(new String(command.getBytes(),"ISO-8859-1"));
    		out.flush();
        	char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) in.read();
            while (true) {
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        byte[] temp = sb.toString().getBytes("iso8859-1");
                        return new String(temp, "GBK");
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
   
	public void disconnect() {
		try {
			telnet.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}