package org.phoenix.web.dao.impl;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.CaseLogBean;
import org.phoenix.web.dao.ICaseLogDao;
import org.springframework.stereotype.Repository;

@Repository
public class CaseLogDao extends BaseDao<CaseLogBean> implements ICaseLogDao{

	@Override
	public CaseLogBean getCaseLog(int id) {
		return super.load(id);
	}

	@Override
	public Pager<CaseLogBean> getCaseLogPagerByBatchLog(int batchLogId) {
		return super.find("from CaseLogBean c where c.batchLogBean.id="+batchLogId);
	}

	@Override
	public Pager<CaseLogBean> getCaseLogPagerByScenarioLog(int scenarioLogId) {
		return super.find("from CaseLogBean where scenarioLogBeanId="+scenarioLogId);	
	}

	@Override
	public void deleteCaseLog(int id) {
		super.delete(id);
	}

}
