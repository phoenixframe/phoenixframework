package org.phoenix.web.dao.impl;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.ScenarioLogBean;
import org.phoenix.web.dao.IScenarioLogDao;
import org.springframework.stereotype.Repository;

@Repository
public class ScenarioLogDao extends BaseDao<ScenarioLogBean> implements IScenarioLogDao{

	@Override
	public void deleteLog(int id) {
		super.delete(id);
	}

	@Override
	public ScenarioLogBean getLog(int id) {
		return super.load(id);
	}

	@Override
	public Pager<ScenarioLogBean> getLogPager(int batchId) {
		return super.find("from ScenarioLogBean s where s.batchLogBean.id="+batchId);
	}

}
