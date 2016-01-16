package org.phoenix.web.service.impl;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.UnitLogBean;
import org.phoenix.web.dao.IUnitLogDao;
import org.phoenix.web.service.IUnitLogService;
import org.springframework.stereotype.Service;

/**
 * 日志操作单元服务
 * @author mengfeiyang
 *
 */
@Service
public class UnitLogService implements IUnitLogService{

	private IUnitLogDao unitLogDao;
	public IUnitLogDao getUnitLogDao() {
		return unitLogDao;
	}
	@Resource
	public void setUnitLogDao(IUnitLogDao unitLogDao) {
		this.unitLogDao = unitLogDao;
	}

	@Override
	public void deleteUnitLog(int id) {
		unitLogDao.deleteUnitLog(id);
	}

	@Override
	public UnitLogBean getUnitLog(int id) {
		return unitLogDao.getUnitLogBean(id);
	}

	@Override
	public Pager<UnitLogBean> getUnitLogPager(int caseLogId) {
		return unitLogDao.getUnitLogBeanPager(caseLogId);
	}

}
