package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.PhoenixJmeterBean;
import org.phoenix.web.dao.IPerformanceDao;
import org.phoenix.web.service.IPerformanceService;
import org.springframework.stereotype.Service;

@Service
public class PerformanceService implements IPerformanceService{
	private IPerformanceDao performanceDao;
	
	public IPerformanceDao getPerformanceDao() {
		return performanceDao;
	}
	@Resource
	public void setPerformanceDao(IPerformanceDao performanceDao) {
		this.performanceDao = performanceDao;
	}

	@Override
	public PhoenixJmeterBean add(PhoenixJmeterBean jmeterBean) {
		return performanceDao.add(jmeterBean);
	}

	@Override
	public void delete(int id) {
		performanceDao.delete(id);
	}

	@Override
	public void update(PhoenixJmeterBean jmeterBean) {
		performanceDao.update(jmeterBean);
	}

	@Override
	public PhoenixJmeterBean getJmeterBean(int id) {
		return performanceDao.load(id);
	}

	@Override
	public List<PhoenixJmeterBean> getJmeterBeanList(int uid) {
		return performanceDao.getJmeterBeansByUid(uid);
	}

	@Override
	public Pager<PhoenixJmeterBean> getJmeterBeanPager(int uid) {
		return performanceDao.getJmeterBeansPagerByUid(uid);
	}

	@Override
	public Pager<PhoenixJmeterBean> getJmeterBeansPagerByKeyWord(int uid,String keyWord) {
		return performanceDao.getJmeterBeansPagerByKeyWord(uid, keyWord);
	}
}
