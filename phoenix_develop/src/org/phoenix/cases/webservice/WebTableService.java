package org.phoenix.cases.webservice;

import java.util.LinkedList;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.phoenix.action.WebElementActionProxy;
import org.phoenix.api.utils.XmlParser;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * 使用phoenix做接口测试的案例,：<br>
 * 设置代理<br>
 * 若对wsdl形式的接口做测试，则wsdl的文件需要以Dom方式解析。使用WebResponse中的Dom即可。
 * @author mengfeiyang
 */
public class WebTableService extends WebElementActionProxy{
	private static String caseName = "接口测试用例";
	public WebTableService() {
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init(caseName,arg0);
		//HttpUnitOptions.setDefaultCharacterSet("UTF-8"); //设置页面默认编码
		//HttpUnitOptions.setExceptionsThrownOnScriptError(false); //禁用js语法检测
		//为将要访问的地址设置Host或代理
		HttpClient client = webProxy.webAPIAction().getHttpClientWithProxy("10.138.65.218", 80, "", "");
		HttpResponse resp = webProxy.webAPIAction().getResponseByHttpClient(client, "http://dianjing.eapidoc.360.cn/#api-Account-account_getcertlist", "get", null);
		try {
			String txt = XmlParser.getInstance().parserXmlContent(
					IOUtils.toString(resp.getEntity().getContent()))
					.getNodeNameAndValues("//*[@id=\"api-Account-account_getcertlist-1.0.0\"]/table[1]/tbody/tr[1]/td[1]")
					.toString();
			System.out.println(txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getUnitLog();
	}
}

