package org.phoenix.test;

import java.util.LinkedList;

import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * 测试类
 * @author mengfeiyang
 *
 */
public class PhoenixWebDriverTest extends ActionProxy {
	public static int caseName=1;
	public PhoenixWebDriverTest() {
	}
	
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean){
		init(caseLogBean);
		px.webAPI().openNewWindowByIE("http://www.baidu.com");
		px.webAPI().webElement("#kw",LocatorType.ID).setText("123");
		String s = phoenix.webAPI().webElement("#su",null).getAttrValue("value");
		System.out.println(s);
		ElementsCollection el = phoenix.webAPI().webElement("").getElements();
		for(SelenideElement se : el){
			se.val();
		}
		phoenix.webAPI().webElement("click").click();
		phoenix.checkPoint().checkIsEqual("百度一下", s,null);
		phoenix.webAPI().closeWindow();
		
		return super.getUnitLog();
	}
}
