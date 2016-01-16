package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.model.PerfLogModel;
import org.phoenix.web.dao.IPerfLogDao;
import org.phoenix.web.service.IPerfLogService;
import org.springframework.stereotype.Service;

@Service
public class PerfLogService implements IPerfLogService{
	private IPerfLogDao perfLogDao;
	
	public IPerfLogDao getPerfLogDao() {
		return perfLogDao;
	}
	@Resource
	public void setPerfLogDao(IPerfLogDao perfLogDao) {
		this.perfLogDao = perfLogDao;
	}

	@Override
	public List<PerfLogModel> loadPerfLogModels(int perfCaseId) {
		return perfLogDao.loadPerfLogModels(perfCaseId);
	}
}
