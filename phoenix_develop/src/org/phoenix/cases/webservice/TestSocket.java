package org.phoenix.cases.webservice;

import java.util.LinkedList;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.utils.NetTelnet;

/**
 * phoenixframe平台连接socket服务器，并接收返回值的示例
 * @author mengfeiyang
 *
 */
public class TestSocket extends WebElementActionProxy{
	private static String caseName = "";
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init(caseName,arg0);
		
		NetTelnet n = new NetTelnet("10.161.xx.xx",8889);
		System.out.println(n.sendCommand("test","Password:"));//向服务器发送test指令，并接收结尾为Password:关键字的字符
		n.disconnect();
		
		return getUnitLog(); 
	}

}
