package org.phoenix.cases;

import java.util.LinkedList;

import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * js执行器测试
 * @author mengfeiyang
 *
 */
public class TestJsExecutor extends ActionProxy{
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		phoenix.webAPI().setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		phoenix.webAPI().openNewWindowByFirefox("http://1.163.com");
		//phoenix.webAPI().openNewWindowByIE("http://1.163.com");
		//phoenix.webAPI().openNewWindowByPhantomJs("http://1.163.com");
		phoenix.webAPI().webElement("#button",null).click();//出错截图测试
		String oos = phoenix.webAPI().webElement("//*[@id=\"pro-view-4\"]/div/div/span",LocatorType.XPATH).getText();
		phoenix.checkPoint().checkIsMatcher("夺宝", oos);//校验oos中是否包含夺宝字段
		phoenix.webAPI().executeScript("alert(12);");//在指定页面弹出一个alert
		phoenix.webAPI().sleep(3000);
		phoenix.webAPI().closeWindow();
		
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		TestJsExecutor t = new TestJsExecutor();
		
		LinkedList<UnitLogBean> ll = t.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}

}
