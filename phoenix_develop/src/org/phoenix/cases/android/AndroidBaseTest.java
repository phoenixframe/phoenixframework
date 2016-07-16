package org.phoenix.cases.android;

import java.util.LinkedList;

import org.openqa.selenium.WebElement;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * android基本操作测试
 * @author mengfeiyang
 *
 */
public class AndroidBaseTest extends ActionProxy{
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		phoenix.androidAPI().openAndroidAppBySelendroidWithEmulator("F:\\baozhanggl.apk");//apk的物理路径
		WebElement el = phoenix.androidAPI().linkFinder("#TextField1", null);
		String tagN = el.getTagName();
		String valN = el.getText();
		System.out.println("TagName:"+tagN+"    value:"+valN);
		//webProxy.checkPoint().checkIsNull(tagN);
		//webProxy.checkPoint().checkIsEqual("Hello", valN);
		phoenix.androidAPI().element("#TextField2", null).setText("shuruceshi");
		phoenix.androidAPI().element("#gaoji", null).click();
		phoenix.androidAPI().element("#chushimima", LocatorType.ID).setText("test123");
		//根据XPath定位app元素，XPath无需手写，用例编写过程中可通过录制方式获取
		phoenix.androidAPI().element("(//TextView[@id='text1'])[1]", LocatorType.XPATH).click();
		phoenix.androidAPI().element("(//CheckedTextView[@id='text1'])[2]", LocatorType.XPATH).click();
		WebElement v = phoenix.androidAPI().linkFinder("//Button[@id='button1']", LocatorType.XPATH);
		System.out.println(v.getText());
		phoenix.checkPoint().checkIsEqual("查询", v.getText());
		phoenix.androidAPI().element("//Button[@id='button1']", LocatorType.XPATH).click();
		phoenix.androidAPI().pressKeyByKeyboard("\uE100");//操作Android硬件，本次点击的是返回键
		phoenix.checkPoint().checkNotNull(phoenix.androidAPI().getSelendroidDriver().getBrightness());
		phoenix.androidAPI().setBrightness(80);//调整Android屏幕的亮度为80
		phoenix.androidAPI().setBrightness(40);
		phoenix.androidAPI().closeSelendroidServer();//关闭本次与Android设备上server的连接
		
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
