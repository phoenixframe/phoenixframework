package org.phoenix.proxy;

import java.util.LinkedList;

import org.phoenix.api.IProxy;
import org.phoenix.api.impl.ProxyAction;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * WebElementAction代理
 * @author mengfeiyang
 *	
 */
public abstract class ActionProxy {
	private LinkedList<UnitLogBean> unitLog = new LinkedList<UnitLogBean>();
	protected IProxy phoenix;
	protected IProxy px;

	public void init(CaseLogBean caseLogBean) {
		px = phoenix = new ProxyAction(unitLog,caseLogBean);
	}
	
	public LinkedList<UnitLogBean> getUnitLog() {
		return unitLog;
	}
	
	public abstract LinkedList<UnitLogBean> run(CaseLogBean caseLogBean);
}
