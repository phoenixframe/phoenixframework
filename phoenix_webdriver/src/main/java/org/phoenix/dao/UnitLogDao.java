package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.UnitLogBean;

public class UnitLogDao extends HibernateDaoImpl<UnitLogBean>{
	
	public void addUnitLog(UnitLogBean unitLog){
	     super.add(unitLog);	
	}
	
	public void addBatchUnitLog(List<UnitLogBean> unitLogs){
		super.addBatchData(unitLogs);
	}
	
	public UnitLogBean getUnitLog(int id){
		return super.load(id);
	}
	
	public List<UnitLogBean> getUnitLogList(int caseLogId){
		return super.loadAll("from UnitLogBean u where u.batchLogBean.id="+caseLogId);
	}

}
