package org.phoenix.cases;

import java.util.LinkedList;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * js执行器测试
 * @author mengfeiyang
 *
 */
public class TestJsExecutor extends WebElementActionProxy{
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init("",arg0);
		webProxy.setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		webProxy.openNewWindowByFirefox("http://1.163.com");
		//webProxy.openNewWindowByIE("http://1.163.com");
		//webProxy.openNewWindowByPhantomJs("http://1.163.com");
		webProxy.webElement("#button").click();//出错截图测试
		String oos = webProxy.webElement("//*[@id=\"pro-view-4\"]/div/div/span",LocatorType.XPATH).getText();
		webProxy.checkPoint().checkIsMatcher("夺宝", oos);//校验oos中是否包含夺宝字段
		webProxy.executeScript("alert(12);");//在指定页面弹出一个alert
		webProxy.sleep(3000);
		webProxy.closeWindow();
		
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
