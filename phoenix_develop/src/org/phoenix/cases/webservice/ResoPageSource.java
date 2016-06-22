package org.phoenix.cases.webservice;

import java.util.LinkedList;

import org.jsoup.Connection.Response;
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
		Response resp = webProxy.webAPIAction().getResponseByJsoupByGet("http://www.baidu.com");
		int resCode = resp.statusCode();
		webProxy.checkPoint().checkIsEqual(resCode, 201);
		System.out.println(resp.body());
		return getUnitLog();
	}
	public static void main(String[] args) {
		ResoPageSource resoSource = new ResoPageSource();
		LinkedList<UnitLogBean> ll = resoSource.run(new CaseLogBean());
		for(UnitLogBean l : ll)System.out.println(l.getContent());
	}
}

