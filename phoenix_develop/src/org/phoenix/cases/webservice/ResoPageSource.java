package org.phoenix.cases.webservice;

import java.util.LinkedList;

import org.apache.http.HttpResponse;
import org.phoenix.action.WebElementActionProxy;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * 使用phoenix做接口测试的案例<br>
 * @author mengfeiyang
 */
public class ResoPageSource extends WebElementActionProxy{
	public ResoPageSource() {
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init("",arg0);
		//HttpUnitOptions.setDefaultCharacterSet("UTF-8"); //设置页面默认编码
		//HttpUnitOptions.setExceptionsThrownOnScriptError(false); //禁用js语法检测
		HttpResponse resp = webProxy.webAPIAction().getResponseByHttpClientWithGet("http://www.baidu.com");
		int resCode = resp.getStatusLine().getStatusCode();
		webProxy.checkPoint().checkIsEqual(resCode, 201);
		return getUnitLog();
	}
	public static void main(String[] args) {
		ResoPageSource resoSource = new ResoPageSource();
		LinkedList<UnitLogBean> ll = resoSource.run(new CaseLogBean());
		for(UnitLogBean l : ll)System.out.println(l.getContent());
	}
}

