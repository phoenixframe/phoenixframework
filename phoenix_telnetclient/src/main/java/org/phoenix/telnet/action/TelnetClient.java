package org.phoenix.telnet.action;

import org.phoenix.telnet.telnetclient.NetTelnet;

/**
 * 对Socket服务作操作
 * @author mengfeiyang
 *
 */
public class TelnetClient implements ITelnetClient{
	private NetTelnet netTelnet;
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.telnet.action.ITelnetClient#configTelnetClient(java.lang.String, java.lang.String)
	 */
	@Override
	public ITelnetClient configTelnetClient(String ip, int port) {
		netTelnet = new NetTelnet(ip,port);
		return this;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.telnet.action.ITelnetClient#configTelnetClient(java.lang.String, int, java.lang.String)
	 */
	public ITelnetClient configTelnetClient(String ip, int port,String respCharset) {
		netTelnet = new NetTelnet(ip,port,respCharset);
		return this;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.telnet.action.ITelnetClient#sendCommand(java.lang.String, java.lang.String)
	 */
	@Override
	public String sendCommand(String command, String pattern) {
		return netTelnet.sendCommand(command, pattern);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.telnet.action.ITelnetClient#disconnect()
	 */
	@Override
	public void disconnect() {
		netTelnet.disconnect();		
	}

}
