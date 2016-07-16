package org.phoenix.cases;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;
/**
 * 使用数据库中的数据进行参数化
 * @author mengfeiyang
 *
 */
public class TestPhoenixCaseUseDBData extends ActionProxy{
	private static String caseName = "消息测试用例";	
	public TestPhoenixCaseUseDBData() {
		
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		/**加载数据库中保存的多个批次数据的方法**/
		phoenix.commonAPI().addLocator(caseName);
		HashMap<InterfaceBatchDataBean, HashMap<String, String>> datas = phoenix.commonAPI().loadWebCaseDatas(caseName);
		for(Entry<InterfaceBatchDataBean, HashMap<String, String>> data : datas.entrySet()){
			//获取当前批次的期望值及当前批次执行的目标或说明
			String expect = data.getKey().getExpectData();
			String remark = data.getKey().getRemark();
			HashMap<String, String> dataBlocks = data.getValue();//获取数据，并根据数据的名称说明数据的内容
			phoenix.webAPI().openNewWindowByIE(dataBlocks.get("输入数据3"));
			phoenix.webAPI().webElement("#kw",null).setText("1111111");
			String s = phoenix.webAPI().webElement("#su",null).getAttrValue(dataBlocks.get("输入数据2"));
			System.out.println(s);
			phoenix.checkPoint().checkIsEqual(dataBlocks.get("输入数据2"), s);
			phoenix.webAPI().webElement("click").click();
			phoenix.checkPoint().checkIsFalse(s!=null);
			phoenix.webAPI().sleep(Long.parseLong(dataBlocks.get("输入数据1")));
			phoenix.webAPI().closeWindow();
		}
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		LinkedList<UnitLogBean> ls = new TestPhoenixCaseUseDBData().run(new CaseLogBean());
		for(UnitLogBean u : ls){
			System.out.println(u.getContent());
		}
	}
}
