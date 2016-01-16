package org.phoenix.cases.aggregate;

import java.util.LinkedList;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * 在该类中引用了 {@link TestCase0} ,这种引用仅在平台上执行时才会生效
 * @author mengfeiyang
 *
 */
public class TestCase1 extends WebElementActionProxy{
	private String caseName = "用户注册";
	
	//@Test   //使用Jenkins执行此用例的方式,此Test为：org.testng.annotations.Test
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init(caseName,arg0);
		webProxy.addAggregateCase("公共登陆用例");//这是一个外部用例，在平台上执行时，会将该类中的执行体引入到当前类中
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
		webProxy.addAggregateCase("公共退出用例"); //通过调用这个用例，来退出驱动
		
		return getUnitLog();
	}

}
