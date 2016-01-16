package org.phoenix.cases.webservice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.phoenix.action.WebElementActionProxy;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * 使用phoenix做接口测试的案例,：<br>
 * 设置代理<br>
 * 若对wsdl形式的接口做测试，则wsdl的文件需要以Dom方式解析。使用WebResponse中的Dom即可。
 * @author mengfeiyang
 *
 */
public class HostTestByHttpClient extends WebElementActionProxy{
	private static String caseName = "接口测试用例";
	public HostTestByHttpClient() {
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init(caseName,arg0);
		HashMap<String,String> headers = new HashMap<String,String>();
		headers.put("Accept-Language", "Accept-Language: en,zh");
		headers.put("Accept-Charset", "Accept-Charset: iso-8859-1");

		HttpClient httpClient = webProxy.webAPIAction().getHttpClientWithProxy("10.138.65.213", 80, "", "");
		HttpResponse response = webProxy.webAPIAction().getResponseByHttpClient(httpClient, "http://trunk.dianjing.e.360.cn", "get", headers);
		try {			
			System.out.println(Arrays.toString(response.getAllHeaders()));
			System.out.println(IOUtils.toString(response.getEntity().getContent()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		HostTestByHttpClient yw = new HostTestByHttpClient();
		LinkedList<UnitLogBean> ll = yw.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
	
}

