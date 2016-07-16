package org.phoenix.cases;

import java.util.LinkedList;

import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * 使用本地数据作参数化
 * @author mengfeiyang
 *
 */
public class WebUIPhantomjsTest extends ActionProxy{
	private static String caseName = "IE";
	
	public WebUIPhantomjsTest() {
		
	}

	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		phoenix.webAPI().openNewWindowByPhantomJs("http://www.baidu.com");
		phoenix.webAPI().webElement("//*[@id=\"kw\"]",LocatorType.XPATH).setText("selenium");
		phoenix.webAPI().webElement("//*[@id=\"su\"]", LocatorType.XPATH).click();
		System.out.println(phoenix.webAPI().webElement("//*[@id=\"su\"]", LocatorType.XPATH).getAttribute("value"));
		phoenix.webAPI().sleep(4000);
		phoenix.webAPI().closeWindow();	
		
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		WebUIPhantomjsTest t = new WebUIPhantomjsTest();
		LinkedList<UnitLogBean> ll = t.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
}
