package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.CaseBean;
import org.phoenix.web.dao.ICaseDao;
import org.phoenix.web.service.ICaseService;
import org.springframework.stereotype.Service;

@Service
public class CaseService implements ICaseService{
    private ICaseDao caseDao;

	public ICaseDao getCaseDao() {
		return caseDao;
	}
    
	@Resource
	public void setCaseDao(ICaseDao caseDao) {
		this.caseDao = caseDao;
	}
	
	public CaseService() {
	}
	
	public void addCase(CaseBean caseBean){
		caseDao.add(caseBean);
	}

	@Override
	public void delCase(int id) {
		caseDao.delete(id);
	}

	@Override
	public void updateCase(CaseBean caseBean) {
		caseDao.update(caseBean);
	}

	@Override
	public List<CaseBean> getCaseBeanListByUser(int uid) {
		return caseDao.getCaseBeanListByUser(uid);
	}
	
	@Override
	public List<CaseBean> getCaseBeanListByUT(int uid, String taskType) {
		return caseDao.getCaseBeanListByUT(uid, taskType);
	} 
	
	@Override
	public List<CaseBean> getCaseBeanListByScenario(int scenarioId) {
		return caseDao.getCaseBeanListByScenario(scenarioId);
	}

	@Override
	public Pager<CaseBean> getCaseBeanPagerByUser(int uid) {
		return caseDao.getCaseBeanPagerByUser(uid);
	}

	@Override
	public Pager<CaseBean> getCaseBeanPagerByScenario(int scenarioId) {
		return caseDao.getCaseBeanPagerByScenario(scenarioId);
	}

	@Override
	public CaseBean getCaseBean(int id) {
		return caseDao.load(id);
	}

	@Override
	public Pager<CaseBean> getCaseBeanPagerByKeyWord(int uid, String keyword,String keyword2) {
		return caseDao.getCaseBeanPagerByKeyWord(uid, keyword, keyword2);
	}

	@Override
	public CaseBean getCaseBeanByName(String name) {
		return caseDao.getCaseBeanByName(name);
	}
  
}
