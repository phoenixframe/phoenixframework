package org.phoenix.cases.webservice;

import java.util.LinkedList;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebResponse;

/**
 * 使用phoenix做接口测试的案例,：<br>
 * 设置代理<br>
 * 若对wsdl形式的接口做测试，则wsdl的文件需要以Dom方式解析。使用WebResponse中的Dom即可。
 * @author mengfeiyang
 *
 */
public class HostTestByHttpUnit extends WebElementActionProxy{
	private static String caseName = "接口测试用例";
	public HostTestByHttpUnit() {
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init(caseName,arg0);
		HttpUnitOptions.setScriptingEnabled(false); //禁用js
		HttpUnitOptions.setDefaultCharacterSet("UTF-8"); //设置页面默认编码
		HttpUnitOptions.setExceptionsThrownOnScriptError(false); //禁用js语法检测
		
/*		WebConversation webConveration = webProxy.webAPIAction().getWebConversation();
		webConveration.putCookie("username", "zhangsan");
		webConveration.setProxyServer("10.138.65.213", 80);
		WebResponse resp = webProxy.webAPIAction().getResponseByHost("http://trunk.dianjing.e.360.cn","get",webConveration);*/
		
		//方法二
		WebResponse resp = webProxy.webAPIAction().getResponseByHost("http://trunk.dianjing.e.360.cn","get","10.138.65.213",80);
		try {
			System.out.println("==="+resp.getElementWithID("province").getText());
			System.out.println(resp.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
/*		try {
			HttpUnitOptions.setExceptionsThrownOnScriptError(false);
			String a = webProxy.webAPIAction().getResponseByGet("http://www.baidu.com").getText();
			System.out.println(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		HostTestByHttpUnit yw = new HostTestByHttpUnit();
		LinkedList<UnitLogBean> ll = yw.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
	
}

