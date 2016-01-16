package org.phoenix.test;

import java.util.LinkedList;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class PhoenixWebDriverTest extends WebElementActionProxy {
	public static int caseName=1;
	public PhoenixWebDriverTest() {
	}
	
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean){
		init(caseName,caseLogBean);
		webProxy.openNewWindowByIE("http://www.baidu.com");
		webProxy.webElement("#kw",LocatorType.ID).setText("123");
		String s = webProxy.webElement("#su",null).getAttrValue("value");
		System.out.println(s);
		ElementsCollection el = webProxy.webElement("").getElements();
		for(SelenideElement se : el){
			se.val();
		}
		webProxy.webElement("click").click();
		webProxy.checkPoint().checkIsEqual("百度一下", s,null);
		webProxy.sleep(1000);
		webProxy.closeWindow();
		
		return getUnitLog();
	}
}
