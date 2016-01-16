package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.CaseLogBean;

public class CaseLogDao extends HibernateDaoImpl<CaseLogBean>{
	
	public void addCaseLog(CaseLogBean caseLogBean){
		super.add(caseLogBean);
	}
	
	public CaseLogBean getCaseLogBean(int id){
		return super.load(id);
	}
	
	public List<CaseLogBean> getCaseLogBeanList(int scenarioId){
		return super.loadAll("from CaseLogBean where scenarioLogBeanId="+scenarioId);
	}

}
