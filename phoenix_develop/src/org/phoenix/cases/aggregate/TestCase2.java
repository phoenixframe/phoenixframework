package org.phoenix.cases.aggregate;

import java.util.LinkedList;

import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * 该类也是公共用例，但该类不能独立的运行。该类在 {@link Test1} 中被引用
 * @author mengfeiyang
 *
 */
public class TestCase2 extends ActionProxy{
	private String caseName = "公共退出用例";
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		phoenix.webAPI().closeWindow();
		
		return getUnitLog();
	}
}
