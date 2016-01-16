package org.phoenix.action;

import java.util.LinkedList;

import org.phoenix.aop.ActionInvocationHandler;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * WebElementAction代理
 * @author mengfeiyang
 *	
 */
public abstract class WebElementActionProxy {
	private LinkedList<UnitLogBean> unitLog = new LinkedList<UnitLogBean>();
	public ElementAction webProxy;
	public LinkedList<UnitLogBean> getUnitLog() {
		return unitLog;
	}
	public void setUnitLog(LinkedList<UnitLogBean> unitLog) {
		this.unitLog = unitLog;
	}
	
	public abstract LinkedList<UnitLogBean> run(CaseLogBean caseLogBean);
	
	public void init(int caseId,CaseLogBean caseLogBean){
		webProxy = (ElementAction)new ActionInvocationHandler(new WebElementAction(unitLog),unitLog,caseLogBean).getProxy();
		webProxy.addLocator(caseId, caseLogBean);
		webProxy.setWebProxy(webProxy);
	}
	
	public void init(String caseName,CaseLogBean caseLogBean){
		webProxy = (ElementAction)new ActionInvocationHandler(new WebElementAction(unitLog),unitLog,caseLogBean).getProxy();
		webProxy.addLocator(caseName, caseLogBean);
		webProxy.setWebProxy(webProxy);
	}
}
