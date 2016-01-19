package org.phoenix.telnet.action;

/**
 * phoenixframe的socket客户端
 * @author mengfeiyang
 *
 */
public interface ITelnetClient {
	/**
	 * 配置客户端连接
	 * @param ip
	 * @param port
	 */
	ITelnetClient configTelnetClient(String ip,int port);
	ITelnetClient configTelnetClient(String ip,int port,String respCharset);
	/**
	 * 向服务端发送命令
	 * @param command 命令内容
	 * @param pattern 指定接收内容的标识符
	 * @return
	 */
	String sendCommand(String command, String pattern);
	
	/**
	 * 断开与socket服务器的连接
	 */
	void disconnect();
}
