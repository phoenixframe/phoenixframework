package org.phoenix.cases.webservice;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.InterfaceDataBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

import com.meterware.httpunit.WebResponse;

/**
 * 使用phoenix做接口测试的案例,包括两个：<br>
 * 1、使用多批数据对一个接口url做测试<br>
 * 2、不使用多批数据<br>
 * 若对wsdl形式的接口做测试，则wsdl的文件需要以Dom方式解析。使用WebResponse中的Dom即可。
 * @author mengfeiyang
 *
 */
public class ContactJieKou extends ActionProxy{
	private static String caseName = "接口测试用例";
	public ContactJieKou() {}
	//@Test   //使用Jenkins执行此用例的方式,此Test为：org.testng.annotations.Test
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		LinkedHashMap<InterfaceBatchDataBean, List<InterfaceDataBean>> datas = phoenix.commonAPI().loadInterfaceDatas(caseName);
		for(Entry<InterfaceBatchDataBean, List<InterfaceDataBean>> entry : datas.entrySet()){
			InterfaceBatchDataBean iBatchBean = entry.getKey();
			List<InterfaceDataBean> iDatas = entry.getValue();
			System.out.println("--数据批次："+iBatchBean.getId()+"   期望值："+iBatchBean.getExpectData());
			String url ="http://v.youku.com/player/getPlayList/VideoIDS/XNzUwODY4Nzc2/timezone/+08/version/5/source/video?ctype=10&ev=1&password=&";
			for(InterfaceDataBean iData : iDatas)url += iData.getDataName()+"="+iData.getDataContent()+"&";
			url = url.substring(0, url.length()-1);
			WebResponse resp = phoenix.interfaceAPI().getResponseByGet(url);
			try {
				//如果接口返回的数据是json格式，则可以通过jsonPath取出实际值，如果不是json则可以自己通过自定义方式如正则表达式等。
				String actual = phoenix.interfaceAPI().getJSONValue(resp.getText(), "JSON.data[0].dvd.point[3].title");
				//String actual = resp.getElementWithID("su").getText();根据页面中的id，tagName，XPath，Dom等方式取到实际值
				String r = phoenix.checkPoint().checkIsEqual(actual, iBatchBean.getExpectData());//检查点结果入库
				if(r == null)System.out.println("-----测试通过-----");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return getUnitLog();
	}
}
