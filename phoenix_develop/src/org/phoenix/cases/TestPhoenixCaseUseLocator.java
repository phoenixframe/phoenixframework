package org.phoenix.cases;

import java.util.Iterator;
import java.util.LinkedList;

import org.openqa.selenium.By;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

import com.codeborne.selenide.SelenideElement;

/**
 * 定位信息使用本地数据的方法
 * @author mengfeiyang
 *
 */
public class TestPhoenixCaseUseLocator extends ActionProxy{
	
	public TestPhoenixCaseUseLocator() {
		
	}

	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		//webProxy.openNewWindowByIE("http://www.baidu.com");
		//webProxy.openNewWindowByFirefox("http://www.baidu.com");//首先需要指定Firefox.exe的路径，方法是：webProxy.setFirefoxExePath(arg0);
		//webProxy.openNewWindowByChrome("http://www.baidu.com");//首先需要指定ChromeDriver.exe的路径，方法是：webProxy.setChromeDriverExePath(arg0);
		phoenix.webAPI().openNewWindowByPhantomJs("http://www.baidu.com");
		phoenix.webAPI().webElement("#kw",null).setText("1");
		String s = phoenix.webAPI().webElement("#su",null).getAttrValue("value");
		System.out.println(s);
		phoenix.checkPoint().checkIsEqual("百度一下", s);
		phoenix.webAPI().webElement("#su",LocatorType.CSS).click();
		phoenix.checkPoint().checkIsFalse(s!=null);
		phoenix.webAPI().sleep(5000);
		Iterator<SelenideElement> els = phoenix.webAPI().webElementLinkFinder(".s_tab",null).$$(By.tagName("a")).iterator();
		while(els.hasNext()){
			SelenideElement se = els.next();
			if(se.getText().equals("文库")){
				se.click();
				break;
			}
			System.out.println(se.getText());
		}
		phoenix.webAPI().closeWindow();	
		
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
