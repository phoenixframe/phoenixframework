package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.BatchLogBean;
import org.phoenix.web.dao.IBatchLogDao;
import org.phoenix.web.service.IBatchLogService;
import org.springframework.stereotype.Service;

/**
 * 日志批次操作服务
 * @author mengfeiyang
 *
 */
@Service
public class BatchLogService implements IBatchLogService{
	private IBatchLogDao batchLogDao;
	public IBatchLogDao getBatchLogDao() {
		return batchLogDao;
	}
	@Resource
	public void setBatchLogDao(IBatchLogDao batchLogDao) {
		this.batchLogDao = batchLogDao;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.web.service.IBatchLogService#deleteBatchLog(int)
	 */
	@Override
	public void deleteBatchLog(int id) {
		batchLogDao.deleteBatchLog(id);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.web.service.IBatchLogService#getBatchLogPager(int)
	 */
	@Override
	public Pager<BatchLogBean> getBatchLogPager(int uid) {
		return batchLogDao.getBatchLogPager(uid);
	}
	@Override
	public List<BatchLogBean> getBatchLogListByType(String type,int uid) {
		return batchLogDao.getBathLogListByTaskType(type,uid);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.web.service.IBatchLogService#getBatchLogBean(int)
	 */
	@Override
	public BatchLogBean getBatchLogBean(int id) {
		return batchLogDao.getBatchLogBean(id);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.web.service.IBatchLogService#getBatchLogPagerById(int)
	 */
	@Override
	public Pager<BatchLogBean> getBatchLogPagerById(int id) {
		return batchLogDao.getBatchLogPagerById(id);
	}

}
