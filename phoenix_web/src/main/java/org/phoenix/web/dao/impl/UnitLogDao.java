package org.phoenix.web.dao.impl;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.UnitLogBean;
import org.phoenix.web.dao.IUnitLogDao;
import org.springframework.stereotype.Repository;

@Repository
public class UnitLogDao extends BaseDao<UnitLogBean> implements IUnitLogDao{

	@Override
	public UnitLogBean getUnitLogBean(int id) {
		return super.load(id);
	}

	@Override
	public Pager<UnitLogBean> getUnitLogBeanPager(int caseLogId) {
		return super.find("from UnitLogBean u where u.caseLogBean.id="+caseLogId);
	}
	@Override
	public void deleteUnitLog(int id){
		super.delete(id);
	}
}
