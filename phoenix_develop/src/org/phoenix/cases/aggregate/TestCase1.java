package org.phoenix.cases.aggregate;

import java.util.LinkedList;

import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * 在该类中引用了 {@link TestCase0} ,这种引用仅在平台上执行时才会生效
 * @author mengfeiyang
 *
 */
public class TestCase1 extends ActionProxy{	
	//@Test   //使用Jenkins执行此用例的方式,此Test为：org.testng.annotations.Test
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		phoenix.commonAPI().addAggregateCase("公共登陆用例");//这是一个外部用例，在平台上执行时，会将该类中的执行体引入到当前类中
		String errorMsg = phoenix.webAPI().webElement("//*[@id=\"login_form\"]/p/em[2]", LocatorType.XPATH).getText();
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
			phoenix.webAPI().webElement("tos", LocatorType.NAME).setSelected(true);
			phoenix.webAPI().sleep(1000);
		} else {
			phoenix.webAPI().webElement("业务管理", LocatorType.LINKTEXT).click();
			phoenix.webAPI().webElement("//*[@id=\"list_view\"]/div[2]/table/tbody/tr[1]/td[4]/a", LocatorType.XPATH).click();
		}
		phoenix.commonAPI().addAggregateCase("公共退出用例"); //通过调用这个用例，来退出驱动		
		return getUnitLog();
	}
}
