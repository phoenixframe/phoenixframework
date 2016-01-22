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
	private static String caseName = "IE";
	
	public TestPhoenixCaseUseData() {
		
	}

	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseName,caseLogBean);
		webProxy.openNewWindowByIE("http://www.baidu.com");
		webProxy.webElement("//*[@id=\"kw\"]",LocatorType.XPATH).setText("1");
		//String s = webProxy.webElement("clickbutton").getAttrValue("value");
		String s = webProxy.webElement("//*[@id=\"su\"]",LocatorType.XPATH).getAttrValue("value");
		System.out.println(s);
		//如果校验结果返回null，表示验证通过
		webProxy.checkPoint().checkIsEqual("百度一下", s);
		
		//直接使用定位数据方式，如果定位信息是id和class，则LocatorType可以为null或者写css，如：
		//webProxy.webElement("#su", null).click();//此句与下句的执行效果是一样的
		//webProxy.webElement("#su", LocatorType.CSS).click();
		
		//使用数据库中的数据的方式。这种方式无需再指定定位类型，但在填写定位信息时无需指定标识符，比如，
		//如果id是：#su,则录入时录su即可。但数据类型需要选择为：ID。
		webProxy.webElement("clickbutton").click();
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
