package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.ScenarioBean;
import org.phoenix.web.dao.IScenarioDao;
import org.phoenix.web.service.IScenarioService;
import org.springframework.stereotype.Service;

@Service
public class ScenarioService implements IScenarioService{
	private IScenarioDao scenarioDao;

	public IScenarioDao getScenarioDao() {
		return scenarioDao;
	}
    
	@Resource
	public void setScenarioDao(IScenarioDao scenarioDao) {
		this.scenarioDao = scenarioDao;
	}

	@Override
	public void add(ScenarioBean scenarioBean) {
        scenarioDao.add(scenarioBean);		
	}

	@Override
	public void delete(int id) {
        scenarioDao.delete(id);		
	}

	@Override
	public void update(ScenarioBean scenarioBean) {
		scenarioDao.update(scenarioBean);
	}

	@Override
	public ScenarioBean getScenarioBean(int id) {
		return scenarioDao.load(id);
	}

	@Override
	public Pager<ScenarioBean> getScenarioBeanPager(int uid) {
		return scenarioDao.getScenarioBeanPager(uid);
	}

	@Override
	public List<ScenarioBean> getScenarioBeanList(int uid) {
		return scenarioDao.getScenarioBeanList(uid);
	}

	@Override
	public void deleteByUser(int uid) {
		scenarioDao.deleteByUser(uid);
	}

	@Override
	public Pager<ScenarioBean> getScenarioBeanPager(int uid, String keyWord) {
		return scenarioDao.getSceanrioBeanPagerBykeyWord(uid, keyWord);
	}

}
