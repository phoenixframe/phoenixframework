package org.phoenix.cases.lianmeng;

import java.util.LinkedList;

import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * 用例代码开发时的用例，可将定位信息和数据，直接保存在代码中。<br>
 * 调试完成后，可将此用例中的代码整体全部复制并粘贴到控制端对应的用例中，也是可以正常执行的。
 * @author mengfeiyang
 *
 */
public class YeWuGuanLi extends ActionProxy{
	public YeWuGuanLi() {
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		//phoenix.webAPI().setChromeDriverExePath("C:\\Users\\mengfeiyang\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
		//phoenix.webAPI().openNewWindowByChrome("http://lianmeng.360.cn/account");
		//phoenix.webAPI().setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		//phoenix.webAPI().openNewWindowByFirefox("http://lianmeng.360.cn/account");
		
		phoenix.webAPI().openNewWindowByPhantomJs("http://lianmeng.360.cn/account");
		//phoenix.webAPI().openNewWindowByPhantomJs("http://lianmeng.360.cn/account","/home/mengfeiyang/tools/phantomjs-1.9.8-linux-x86_64/bin/phantomjs");
		phoenix.webAPI().webElement("#uname",null).setText("test");
		phoenix.webAPI().webElement("passwd", LocatorType.NAME).setText("123456");
		phoenix.webAPI().webElement("verifyCode", LocatorType.NAME).setText("6g6m");
		phoenix.webAPI().webElement("//*[@id=\"login_form\"]/dl[4]/dd/button",LocatorType.XPATH).click();
		String errorMsg = phoenix.webAPI().webElement("//*[@id=\"login_form\"]/p/em[2]", LocatorType.XPATH).getText();
		System.out.println(errorMsg);
		phoenix.webAPI().sleep(1000);
		String r = phoenix.checkPoint().checkIsEqual("", errorMsg);
		if(!r.contains("null")){
			phoenix.webAPI().webElement("马上注册！", LocatorType.LINKTEXT).click();
			phoenix.webAPI().webElementLinkFinder(".panel-content",null).findElementByLinkText("导航联盟").click();
			phoenix.webAPI().sleep(1000);
			phoenix.webAPI().webElement("#user_name", null).setText("Test123");
			phoenix.webAPI().webElement("#passwd", null).setText("1111");
			phoenix.webAPI().webElement("//*[@id=\"step1\"]/form/dl[3]/dd/input", LocatorType.XPATH).setText("1111");
			phoenix.webAPI().webElement("#ft_company", null).setSelected(true);
			phoenix.webAPI().webElement("company", LocatorType.NAME).setText("111111111111");
			phoenix.webAPI().webElement("taxID", LocatorType.NAME).setText("12312");
			phoenix.webAPI().webElement("contactPerson",LocatorType.NAME).setText("222222222222");
			phoenix.webAPI().webElement("#cell_phone",null).setText("156522755555");
			phoenix.webAPI().webElement("#verifyCode", null).setText("1234");
			phoenix.webAPI().webElement("verifyCode", LocatorType.NAME).setText("1234");
			phoenix.webAPI().webElement("email", LocatorType.NAME).setText("123@123.com");
			phoenix.webAPI().webElement("tos", LocatorType.NAME).setSelected(true);
			phoenix.webAPI().sleep(1000);
		} else {
			phoenix.webAPI().webElement("业务管理", LocatorType.LINKTEXT).click();
			phoenix.webAPI().webElement("//*[@id=\"list_view\"]/div[2]/table/tbody/tr[1]/td[4]/a", LocatorType.XPATH).click();
		}
		phoenix.webAPI().closeWindow();
		
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
