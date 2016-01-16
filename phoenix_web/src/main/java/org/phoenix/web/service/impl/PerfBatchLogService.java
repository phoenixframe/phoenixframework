package org.phoenix.web.service.impl;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.PerfBatchLogModel;
import org.phoenix.web.dao.IPerfBatchDao;
import org.phoenix.web.service.IPerfBatchLogService;
import org.springframework.stereotype.Service;

@Service
public class PerfBatchLogService implements IPerfBatchLogService{
	private IPerfBatchDao perfBatchDao;
	
	public IPerfBatchDao getPerfBatchDao() {
		return perfBatchDao;
	}
	@Resource
	public void setPerfBatchDao(IPerfBatchDao perfBatchDao) {
		this.perfBatchDao = perfBatchDao;
	}

	@Override
	public void deleteBatchLog(int batchId) {
		perfBatchDao.delete(batchId);
	}

	@Override
	public Pager<PerfBatchLogModel> getBatchLogPager(int perfCaseId) {
		return perfBatchDao.loadPerfBatchPager(perfCaseId);
	}
	@Override
	public PerfBatchLogModel load(int batchId) {
		return perfBatchDao.load(batchId);
	}

}
