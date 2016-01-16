package org.phoenix.cases;

import java.util.LinkedList;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * 使用本地数据作参数化
 * @author mengfeiyang
 *
 */
public class TestPhoenixCaseUseData extends WebElementActionProxy{
	private static int caseName = 5;
	
	public TestPhoenixCaseUseData() {
		
	}

	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseName,caseLogBean);
		webProxy.openNewWindowByIE("http://www.baidu.com");
		webProxy.webElement("set").setText("1");
		String s = webProxy.webElement("click").getAttrValue("value");
		System.out.println(s);
		webProxy.checkPoint().checkIsEqual("百度一下", s);
		//直接使用定位数据方式，如果定位信息是id和class，则LocatorType可以为null或者写css，如：
		//webProxy.webElement("#su", null).click();//此句与下句的执行效果是一样的
		//webProxy.webElement("#su", LocatorType.CSS).click();
		
		//使用数据库中的数据的方式。这种方式无需再指定定位类型，如：
		webProxy.webElement("click").click();
		webProxy.checkPoint().checkIsFalse(s!=null);
		webProxy.sleep(100);
		webProxy.closeWindow();	
		
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		TestPhoenixCaseUseData t = new TestPhoenixCaseUseData();
		LinkedList<UnitLogBean> ll = t.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
}
