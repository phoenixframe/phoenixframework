package org.phoenix.cases;

import java.util.LinkedList;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * div形式的弹出框的处理，如登陆
 * @author mengfeiyang
 *
 */
public class TestPhoenixCaseDiv extends WebElementActionProxy{
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init("",arg0);
		webProxy.setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		//webProxy.openNewWindowByFirefox("http://1.163.com");
		webProxy.openNewWindowByIE("http://1.163.com");
		//webProxy.openNewWindowByPhantomJs("http://1.163.com");
		if(webProxy.webElement(".w-msgbox-close", LocatorType.CSS).exists())webProxy.webElement(".w-msgbox-close", LocatorType.CSS).click();
		webProxy.webElement("请登录", LocatorType.LINKTEXT).click();
		
		//以下3个步骤在火狐浏览器下执行时，会报错。但在IE下没有问题
		webProxy.webElementLinkFinder("#pro-view-18",null).$(".w-input-input").setValue("username");
		webProxy.webElementLinkFinder("#pro-view-20",null).$(".w-input-input").sendKeys("password");		
		webProxy.webElementLinkFinder("#pro-view-16",null).findElementByTagName("button").click();
		
		webProxy.closeWindow();
		
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
