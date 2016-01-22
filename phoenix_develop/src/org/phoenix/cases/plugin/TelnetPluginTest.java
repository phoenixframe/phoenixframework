package org.phoenix.cases.plugin;

import java.util.LinkedList;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.plugins.ITelnetClient;

/**
 * Telnet客户端测试
 * @author mengfeiyang
 *
 */
public class TelnetPluginTest extends WebElementActionProxy{
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init("",arg0);
		//连接SocketServer
		ITelnetClient telnet = webProxy.telnetClient().configTelnetClient("localhost", 8889);
		String rs = telnet.sendCommand("!showorders", "datas");//向socketServer发送指令，如果返回值以datas结尾，则终止数据接收
		System.out.println(rs);
		webProxy.checkPoint().checkIsNull(rs);
		String rs2 = telnet.sendCommand("!showusers", "]");
		System.out.println(rs2);
		webProxy.checkPoint().checkIsNull(rs2);
		telnet.disconnect();//断开本次的连接
		
		return getUnitLog(); 
	}
	
	public static void main(String[] args) {
		TelnetPluginTest p = new TelnetPluginTest();
		LinkedList<UnitLogBean> ll = p.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
}
