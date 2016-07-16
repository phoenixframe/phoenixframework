package org.phoenix.cases;

import java.util.HashMap;
import java.util.LinkedList;

import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * 浏览器驱动测试类
 * @author mengfeiyang
 *
 */
public class TestBrowserDriver extends ActionProxy{
	private static String caseName = "消息测试用例";
	
	public TestBrowserDriver() {
		
	}

	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		HashMap<InterfaceBatchDataBean, HashMap<String, String>> datas = phoenix.commonAPI().loadWebCaseDatas(caseName);
		System.out.println(datas);
		phoenix.webAPI().setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		phoenix.webAPI().setChromeDriverExePath("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		phoenix.webAPI().openNewWindowByFirefox("http://www.baidu.com");
		//phoenix.webAPI().openNewWindowByChrome("http://www.baidu.com");
		//phoenix.webAPI().openNewWindowByIE("http://www.baidu.com");
		//phoenix.webAPI().openNewWindowByHtmlUnit("http://www.baidu.com", true, BrowserVersion.INTERNET_EXPLORER);
		//phoenix.webAPI().openNewWindowByPhantomJs("http://www.baidu.com");
		phoenix.webAPI().webElement("//*[@id=\"kw\"]",LocatorType.XPATH).setText("phoenixframe");
		phoenix.webAPI().webElement("//*[@id=\"su\"]", LocatorType.XPATH).click();
		String r = phoenix.webAPI().webElement("//*[@id=\"su\"]", LocatorType.XPATH).getAttribute("value");
		phoenix.checkPoint().checkIsEqual(r, "百度一下");
		phoenix.commonAPI().addLog("我是自定义的");
		phoenix.webAPI().sleep(1000);
		phoenix.webAPI().closeWindow();	
		
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		LinkedList<UnitLogBean> ll = new TestBrowserDriver().run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
}
