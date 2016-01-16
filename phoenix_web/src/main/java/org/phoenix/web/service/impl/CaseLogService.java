package org.phoenix.web.service.impl;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.CaseLogBean;
import org.phoenix.web.dao.ICaseLogDao;
import org.phoenix.web.service.ICaseLogService;
import org.springframework.stereotype.Service;

@Service
public class CaseLogService implements ICaseLogService{
	private ICaseLogDao caseLogDao;
	
	public ICaseLogDao getCaseLogDao() {
		return caseLogDao;
	}
	@Resource
	public void setCaseLogDao(ICaseLogDao caseLogDao) {
		this.caseLogDao = caseLogDao;
	}

	@Override
	public void deleteCaseLog(int id) {
		caseLogDao.deleteCaseLog(id);
	}

	@Override
	public CaseLogBean getCaseLog(int id) {
		return caseLogDao.getCaseLog(id);
	}

	@Override
	public Pager<CaseLogBean> getCaseLogPagerByBatchLog(int batchLogId) {
		return caseLogDao.getCaseLogPagerByBatchLog(batchLogId);
	}

	@Override
	public Pager<CaseLogBean> getCaseLogPagerByScenarioLog(int scenarioLogId) {
		return caseLogDao.getCaseLogPagerByScenarioLog(scenarioLogId);
	}

}
