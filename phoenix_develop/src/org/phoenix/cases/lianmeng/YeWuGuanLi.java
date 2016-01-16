package org.phoenix.cases.lianmeng;

import java.util.LinkedList;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * 用例代码开发时的用例，可将定位信息和数据，直接保存在代码中。<br>
 * 调试完成后，可将此用例中的代码整体全部复制并粘贴到控制端对应的用例中，也是可以正常执行的。
 * @author mengfeiyang
 *
 */
public class YeWuGuanLi extends WebElementActionProxy{
	private static String caseName = "报告查看";
	public YeWuGuanLi() {
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init(caseName,arg0);
		//webProxy.setChromeDriverExePath("C:\\Users\\mengfeiyang\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
		//webProxy.openNewWindowByChrome("http://lianmeng.360.cn/account");
		//webProxy.setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		//webProxy.openNewWindowByFirefox("http://lianmeng.360.cn/account");
		
		webProxy.openNewWindowByPhantomJs("http://lianmeng.360.cn/account");
		//webProxy.openNewWindowByPhantomJs("http://lianmeng.360.cn/account","/home/mengfeiyang/tools/phantomjs-1.9.8-linux-x86_64/bin/phantomjs");
		webProxy.webElement("#uname",null).setText("test");
		webProxy.webElement("passwd", LocatorType.NAME).setText("123456");
		webProxy.webElement("verifyCode", LocatorType.NAME).setText("6g6m");
		webProxy.webElement("//*[@id=\"login_form\"]/dl[4]/dd/button",LocatorType.XPATH).click();
		String errorMsg = webProxy.webElement("//*[@id=\"login_form\"]/p/em[2]", LocatorType.XPATH).getText();
		System.out.println(errorMsg);
		webProxy.sleep(1000);
		String r = webProxy.checkPoint().checkIsEqual("", errorMsg);
		if(!r.contains("null")){
			webProxy.webElement("马上注册！", LocatorType.LINKTEXT).click();
			webProxy.webElementLinkFinder(".panel-content",null).findElementByLinkText("导航联盟").click();
			webProxy.sleep(1000);
			webProxy.webElement("#user_name", null).setText("Test123");
			webProxy.webElement("#passwd", null).setText("1111");
			webProxy.webElement("//*[@id=\"step1\"]/form/dl[3]/dd/input", LocatorType.XPATH).setText("1111");
			webProxy.webElement("#ft_company", null).setSelected(true);
			webProxy.webElement("company", LocatorType.NAME).setText("111111111111");
			webProxy.webElement("taxID", LocatorType.NAME).setText("12312");
			webProxy.webElement("contactPerson",LocatorType.NAME).setText("222222222222");
			webProxy.webElement("#cell_phone",null).setText("156522755555");
			webProxy.webElement("#verifyCode", null).setText("1234");
			webProxy.webElement("verifyCode", LocatorType.NAME).setText("1234");
			webProxy.webElement("email", LocatorType.NAME).setText("123@123.com");
			webProxy.webElement("tos", LocatorType.NAME).setSelected(true);
			webProxy.sleep(1000);
		} else {
			webProxy.webElement("业务管理", LocatorType.LINKTEXT).click();
			webProxy.webElement("//*[@id=\"list_view\"]/div[2]/table/tbody/tr[1]/td[4]/a", LocatorType.XPATH).click();
		}
		webProxy.closeWindow();
		
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		YeWuGuanLi yw = new YeWuGuanLi();
		LinkedList<UnitLogBean> ll = yw.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
	
}
