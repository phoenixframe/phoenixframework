package org.phoenix.cases.android;

import java.util.LinkedList;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * phoenixframe平台的Android浏览器测试用例
 * @author mengfeiyang
 *
 */
public class AndroidWebBrowserTest extends WebElementActionProxy{
	
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init("",arg0);
		webProxy.openAndroidBrowserBySelendroid("http://www.baidu.com");//使用Android自带的浏览器打开百度
		webProxy.webElement("#index-kw", null).setText("test123");
		webProxy.webElement("#index-bn", null).click();
		webProxy.sleep(1000);
		//driver.findElement(By.xpath("//*[@id=\"h-tabs\"]/a[3]/span")).click();
		//点击搜索，XPath编写方式与PC浏览器一样，不同的是，百度的mobile版与PC版元素的名称不一致
		webProxy.webElement("//*[@id=\"h-tabs\"]/a[3]", LocatorType.XPATH).click();
		webProxy.sleep(6000);
		webProxy.closeSelendroidServer();
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		AndroidWebBrowserTest androidBrowser = new AndroidWebBrowserTest();
		LinkedList<UnitLogBean> ll = androidBrowser.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
}
