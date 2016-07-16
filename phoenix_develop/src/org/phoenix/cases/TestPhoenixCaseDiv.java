package org.phoenix.cases;

import java.util.LinkedList;

import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * div形式的弹出框的处理，如登陆
 * @author mengfeiyang
 *
 */
public class TestPhoenixCaseDiv extends ActionProxy{
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		phoenix.webAPI().setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		phoenix.webAPI().openNewWindowByFirefox("http://1.163.com");
		//phoenix.webAPI().openNewWindowByIE("http://1.163.com");
		//phoenix.webAPI().openNewWindowByPhantomJs("http://1.163.com");
		if(phoenix.webAPI().webElement(".w-msgbox-close", LocatorType.CSS).exists())phoenix.webAPI().webElement(".w-msgbox-close", LocatorType.CSS).click();
		phoenix.webAPI().webElement("请登录", LocatorType.LINKTEXT).click();
		
		//以下3个步骤在火狐浏览器下执行时，会报错。但在IE下没有问题
		phoenix.webAPI().webElementLinkFinder("#pro-view-18",null).$(".w-input-input").setValue("username");
		phoenix.webAPI().webElementLinkFinder("#pro-view-20",null).$(".w-input-input").sendKeys("password");		
		phoenix.webAPI().webElementLinkFinder("#pro-view-16",null).findElementByTagName("button").click();
		
		phoenix.webAPI().closeWindow();
		
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		TestPhoenixCaseDiv t = new TestPhoenixCaseDiv();
		
		LinkedList<UnitLogBean> ll = t.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}

}
