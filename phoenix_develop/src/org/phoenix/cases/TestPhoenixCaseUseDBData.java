package org.phoenix.cases;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.UnitLogBean;
/**
 * 使用数据库中的数据进行参数化
 * @author mengfeiyang
 *
 */
public class TestPhoenixCaseUseDBData extends WebElementActionProxy{
	private static String caseName = "消息测试用例";	
	public TestPhoenixCaseUseDBData() {
		
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseName,caseLogBean);
		
		/**加载数据库中保存的多个批次数据的方法**/
		HashMap<InterfaceBatchDataBean, HashMap<String, String>> datas = webProxy.loadWebCaseDatas(caseName);
		for(Entry<InterfaceBatchDataBean, HashMap<String, String>> data : datas.entrySet()){
			//获取当前批次的期望值及当前批次执行的目标或说明
			String expect = data.getKey().getExpectData();
			String remark = data.getKey().getRemark();
			HashMap<String, String> dataBlocks = data.getValue();//获取数据，并根据数据的名称说明数据的内容
			webProxy.openNewWindowByIE(dataBlocks.get("输入数据3"));
			webProxy.webElement("set").setText("1111111");
			String s = webProxy.webElement("click").getAttrValue(dataBlocks.get("输入数据2"));
			System.out.println(s);
			webProxy.checkPoint().checkIsEqual(dataBlocks.get("输入数据2"), s);
			webProxy.webElement("click").click();
			webProxy.checkPoint().checkIsFalse(s!=null);
			webProxy.sleep(Long.parseLong(dataBlocks.get("输入数据1")));
			webProxy.closeWindow();
		}
		return getUnitLog();
	}
}
