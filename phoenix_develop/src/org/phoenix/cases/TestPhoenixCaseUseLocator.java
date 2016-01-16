package org.phoenix.cases;

import java.util.Iterator;
import java.util.LinkedList;

import org.openqa.selenium.By;
import org.phoenix.action.WebElementActionProxy;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

import com.codeborne.selenide.SelenideElement;

/**
 * 定位信息使用本地数据的方法
 * @author mengfeiyang
 *
 */
public class TestPhoenixCaseUseLocator extends WebElementActionProxy{
	private static String caseName = "消息测试用例";//用例的名称或id都能加载到该用例下的定位信息和数据
	//private static int caseName = 5;//用例的id
	
	public TestPhoenixCaseUseLocator() {
		
	}

	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseName,caseLogBean);
		
		//webProxy.openNewWindowByIE("http://www.baidu.com");
		//webProxy.openNewWindowByFirefox("http://www.baidu.com");//首先需要指定Firefox.exe的路径，方法是：webProxy.setFirefoxExePath(arg0);
		//webProxy.openNewWindowByChrome("http://www.baidu.com");//首先需要指定ChromeDriver.exe的路径，方法是：webProxy.setChromeDriverExePath(arg0);
		webProxy.openNewWindowByPhantomJs("http://www.baidu.com");
		webProxy.webElement("#kw",null).setText("1");
		String s = webProxy.webElement("#su",null).getAttrValue("value");
		System.out.println(s);
		webProxy.checkPoint().checkIsEqual("百度一下", s);
		webProxy.webElement("#su",LocatorType.CSS).click();
		webProxy.checkPoint().checkIsFalse(s!=null);
		webProxy.sleep(5000);
		Iterator<SelenideElement> els = webProxy.webElementLinkFinder(".s_tab",null).$$(By.tagName("a")).iterator();
		while(els.hasNext()){
			SelenideElement se = els.next();
			if(se.getText().equals("文库")){
				se.click();
				break;
			}
			System.out.println(se.getText());
		}
		webProxy.closeWindow();	
		
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		TestPhoenixCaseUseLocator t = new TestPhoenixCaseUseLocator();
		LinkedList<UnitLogBean> ll = t.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
}
