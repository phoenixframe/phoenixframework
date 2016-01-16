package org.phoenix.cases.webservice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.api.utils.RandomUtils;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.parsing.HTMLParserFactory;

/**
 * 获取webtable的内容
 * @author mengfeiyang
 *
 */
public class WebTableTest extends WebElementActionProxy{
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init("",arg0);
		List<String[]> dataList = new ArrayList<String[]>();
		webProxy.setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		webProxy.openNewWindowByFirefox("http://dianjing.eapidoc.360.cn/#api-Account-account_getcertlist", "10.138.65.218",80);
		
		//webProxy.openNewWindowByPhantomJs("http://dianjing.eapidoc.360.cn/#api-Account-account_getcertlist", "10.138.65.218",80);
		//webProxy.openNewWindowByHtmlUnit("http://dianjing.eapidoc.360.cn/#api-Account-account_getcertlist",true,BrowserVersion.CHROME);
		//webProxy.openNewWindowByHtmlUnit("http://dianjing.eapidoc.360.cn/#api-Account-account_getcertlist",true,BrowserVersion.CHROME,"10.138.65.218",80);
		for(int tr=1;tr<13;tr++){
			String[] dataUnit = new String[2];
			String txt1 = webProxy.webElement("//*[@id=\"api-Account-account_getcertlist-1.0.0\"]/table[1]/tbody/tr["+tr+"]/td[1]", LocatorType.XPATH).getText();
			String txt2 = webProxy.webElement("//*[@id=\"api-Account-account_getcertlist-1.0.0\"]/table[1]/tbody/tr["+tr+"]/td[2]/p", LocatorType.XPATH).getText();
			dataUnit[0]=txt1;
			dataUnit[1]=txt2;
			dataList.add(dataUnit);
		}
		webProxy.closeWindow();;
		for(String[] arr:dataList){
			//根据参数类型自动生成对应的数据类型
			System.out.println("数据名称："+arr[0]+" 数据类型："+arr[1]+" 生成的数据："+createData(arr[1]));
		}
		
		return getUnitLog();
	}
	
	public String createData(String type){
		switch(type){
		case "Number":return Integer.toString(RandomUtils.getRanInt(30));
		case "String":return RandomUtils.getRanCHS(5);
		default:return null;
		}
	}
	
	public static void main(String[] args) {
		WebTableTest t = new WebTableTest();
		
		LinkedList<UnitLogBean> ll = t.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}

}
