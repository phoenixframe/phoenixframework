package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.LocatorBean;
import org.phoenix.web.dao.ILocatorDao;
import org.phoenix.web.service.ILocatorService;
import org.springframework.stereotype.Service;

/**
 * 定位信息服务类
 * @author mengfeiyang
 *
 */
@Service
public class LocatorService implements ILocatorService{
	private ILocatorDao locatorDao;

	public ILocatorDao getLocatorDao() {
		return locatorDao;
	}
    @Resource
	public void setLocatorDao(ILocatorDao locatorDao) {
		this.locatorDao = locatorDao;
	}
    
    /*
     * (non-Javadoc)
     * @see org.phoenix.web.service.ILocatorService#addLocator(org.phoenix.model.LocatorBean)
     */
	@Override
	public void addLocator(LocatorBean locatorBean) {
		locatorDao.add(locatorBean);
	}
    /*
     * (non-Javadoc)
     * @see org.phoenix.web.service.ILocatorService#delLocator(int)
     */
	@Override
	public void delLocator(int id) {
		locatorDao.delete(id);
		
	}
    
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.web.service.ILocatorService#updateLocator(org.phoenix.model.LocatorBean)
	 */
	@Override
	public void updateLocator(LocatorBean locatorBean) {
		locatorDao.update(locatorBean);
		
	}
    /*
     * (non-Javadoc)
     * @see org.phoenix.web.service.ILocatorService#getLocatorBean(int)
     */
	@Override
	public LocatorBean getLocatorBean(int id) {
		return locatorDao.load(id);
	}
    /*
     * (non-Javadoc)
     * @see org.phoenix.web.service.ILocatorService#getLocatorBeanList(int)
     */
	@Override
	public List<LocatorBean> getLocatorBeanList(int caseId) {
		return locatorDao.getLocatorBeanListByCase(caseId);
	}
    /*
     * (non-Javadoc)
     * @see org.phoenix.web.service.ILocatorService#getLoatorBeanPager(int)
     */
	@Override
	public Pager<LocatorBean> getLoatorBeanPager(int caseId) {
		return locatorDao.getLocatorBeanListByPager(caseId);
	}
	@Override
	public void addLocatorBeanList(List<LocatorBean> list) {
		locatorDao.addBatchData(list);
	}

}
