package org.phoenix.cases.webservice;

import java.util.LinkedList;

import org.jsoup.Connection.Response;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * 使用phoenix做接口测试的案例<br>
 * @author mengfeiyang
 */
public class ResoPageSource extends ActionProxy{
	public ResoPageSource() {
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		Response resp = phoenix.interfaceAPI().getResponseByJsoupByGet("http://www.baidu.com");
		int resCode = resp.statusCode();
		phoenix.checkPoint().checkIsEqual(resCode, 201);
		System.out.println(resp.body());
		return getUnitLog();
	}
	public static void main(String[] args) {
		ResoPageSource resoSource = new ResoPageSource();
		LinkedList<UnitLogBean> ll = resoSource.run(new CaseLogBean());
		for(UnitLogBean l : ll)System.out.println(l.getContent());
	}
}

