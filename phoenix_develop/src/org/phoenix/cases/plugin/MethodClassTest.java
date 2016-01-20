package org.phoenix.cases.plugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.UnitLogBean;

/**
 * svn客户端测试
 * @author mengfeiyang
 *
 */
public class MethodClassTest extends WebElementActionProxy   {
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0){
		init("方法选择测试",arg0);
		HashMap<InterfaceBatchDataBean, HashMap<String, String>>  datas = webProxy.loadWebCaseDatas("方法选择测试");
		for(Entry<InterfaceBatchDataBean, HashMap<String, String>> d : datas.entrySet()){
			InterfaceBatchDataBean key = d.getKey();
			HashMap<String, String> value = d.getValue();
			webProxy.checkPoint().checkIsEqual(value.get("2"), value.get("1"));
			System.out.println(key.getExpectData());
		}
		
		return getUnitLog(); 
	}
}
