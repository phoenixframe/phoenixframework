package org.phoenix.cases.android;

import java.util.LinkedList;

import org.openqa.selenium.WebElement;
import org.phoenix.action.WebElementActionProxy;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * android基本操作测试
 * @author mengfeiyang
 *
 */
public class AndroidBaseTest extends WebElementActionProxy{
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init("",arg0);
		webProxy.openAndroidAppBySelendroidWithEmulator("F:\\baozhanggl.apk");//apk的物理路径
		WebElement el = webProxy.appElementLinkFinder("#TextField1", null);
		String tagN = el.getTagName();
		String valN = el.getText();
		System.out.println("TagName:"+tagN+"    value:"+valN);
		//webProxy.checkPoint().checkIsNull(tagN);
		//webProxy.checkPoint().checkIsEqual("Hello", valN);
		webProxy.appElement("#TextField2", null).appElementSetText("shuruceshi");
		webProxy.appElement("#gaoji", null).appElementClick();
		webProxy.appElement("#chushimima", LocatorType.ID).appElementSetText("test123");
		//根据XPath定位app元素，XPath无需手写，用例编写过程中可通过录制方式获取
		webProxy.appElement("(//TextView[@id='text1'])[1]", LocatorType.XPATH).appElementClick();
		webProxy.appElement("(//CheckedTextView[@id='text1'])[2]", LocatorType.XPATH).appElementClick();
		WebElement v = webProxy.appElementLinkFinder("//Button[@id='button1']", LocatorType.XPATH);
		System.out.println(v.getText());
		webProxy.checkPoint().checkIsEqual("查询", v.getText());
		webProxy.appElement("//Button[@id='button1']", LocatorType.XPATH).appElementClick();
		webProxy.pressKeyByKeyboard("\uE100");//操作Android硬件，本次点击的是返回键
		webProxy.checkPoint().checkNotNull(webProxy.getSelendroidDriver().getBrightness());
		webProxy.setBrightness(80);//调整Android屏幕的亮度为80
		webProxy.setBrightness(40);
		webProxy.closeSelendroidServer();//关闭本次与Android设备上server的连接
		
		return getUnitLog(); 
	}
	
	public static void main(String[] args) {
		AndroidBaseTest p = new AndroidBaseTest();
		LinkedList<UnitLogBean> ll = p.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
}
