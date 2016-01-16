package org.phoenix.web.service.impl;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.ScenarioLogBean;
import org.phoenix.web.dao.IScenarioLogDao;
import org.phoenix.web.service.IScenarioLogService;
import org.springframework.stereotype.Service;

@Service
public class ScenarioLogService implements IScenarioLogService{
	private IScenarioLogDao scenarioLogDao;

	public IScenarioLogDao getScenarioLogDao() {
		return scenarioLogDao;
	}
	@Resource
	public void setScenarioLogDao(IScenarioLogDao scenarioLogDao) {
		this.scenarioLogDao = scenarioLogDao;
	}
	
	public void deleteLog(int id){
		scenarioLogDao.delete(id);
	}
	
	public ScenarioLogBean getLog(int id){
		return scenarioLogDao.getLog(id);
	}
	
	public Pager<ScenarioLogBean> getLogPager(int batchLogId){
		return scenarioLogDao.getLogPager(batchLogId);
	}
	
}
