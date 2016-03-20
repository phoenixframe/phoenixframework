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
public class WebUIPhantomjsTest extends WebElementActionProxy{
	private static String caseName = "IE";
	
	public WebUIPhantomjsTest() {
		
	}

	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseName,caseLogBean);
		webProxy.openNewWindowByPhantomJs("http://www.baidu.com");
		webProxy.webElement("//*[@id=\"kw\"]",LocatorType.XPATH).setText("selenium");
		webProxy.webElement("//*[@id=\"su\"]", LocatorType.XPATH).click();
		System.out.println(webProxy.webElement("//*[@id=\"su\"]", LocatorType.XPATH).getAttribute("value"));
		webProxy.sleep(4000);
		webProxy.closeWindow();	
		
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
