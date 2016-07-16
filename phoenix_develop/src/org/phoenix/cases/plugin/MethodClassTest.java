package org.phoenix.cases.plugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * svn客户端测试
 * @author mengfeiyang
 *
 */
public class MethodClassTest extends ActionProxy   {
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean){
		init(caseLogBean);
		HashMap<InterfaceBatchDataBean, HashMap<String, String>>  datas = phoenix.commonAPI().loadWebCaseDatas("方法选择测试");
		for(Entry<InterfaceBatchDataBean, HashMap<String, String>> d : datas.entrySet()){
			InterfaceBatchDataBean key = d.getKey();
			HashMap<String, String> value = d.getValue();
			phoenix.checkPoint().checkIsEqual("1", value.get("21"));
			System.out.println(key.getExpectData());
		}
		
		return getUnitLog(); 
	}
	public static void main(String[] args) {
		MethodClassTest m = new MethodClassTest();
		LinkedList<UnitLogBean> ls = m.run(new CaseLogBean());
		for(UnitLogBean u : ls){
			System.out.println(u.getContent());
		}
	}
}
