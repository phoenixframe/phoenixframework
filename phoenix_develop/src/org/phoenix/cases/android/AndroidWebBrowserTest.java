package org.phoenix.cases.android;

import java.util.LinkedList;

import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * phoenixframe平台的Android浏览器测试用例
 * @author mengfeiyang
 *
 */
public class AndroidWebBrowserTest extends ActionProxy{
	
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		//1.使用AndroidAPI打开浏览器
		phoenix.androidAPI().openAndroidBrowserBySelendroid("http://www.baidu.com");//使用Android自带的浏览器打开百度
		//2.使用webAPI来操作浏览器内的元素，与操作Pc浏览器方式一致
		phoenix.webAPI().webElement("#index-kw", null).setText("test123");
		phoenix.webAPI().webElement("#index-bn", null).click();
		phoenix.commonAPI().sleep(1000);
		//driver.findElement(By.xpath("//*[@id=\"h-tabs\"]/a[3]/span")).click();
		//点击搜索，XPath编写方式与PC浏览器一样，不同的是，百度的mobile版与PC版元素的名称不一致
		phoenix.webAPI().webElement("//*[@id=\"h-tabs\"]/a[3]", LocatorType.XPATH).click();
		phoenix.commonAPI().sleep(6000);
		//使用AndroidAPI关闭连接
		phoenix.androidAPI().closeSelendroidServer();
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
