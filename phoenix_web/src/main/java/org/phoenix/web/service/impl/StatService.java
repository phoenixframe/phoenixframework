package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.web.dao.IStatDao;
import org.phoenix.web.dto.StatisticsDTO;
import org.phoenix.web.service.IStatService;
import org.springframework.stereotype.Service;

@Service
public class StatService implements IStatService{
	private IStatDao statDao;
	
	public IStatDao getStatDao() {
		return statDao;
	}
	@Resource
	public void setStatDao(IStatDao statDao) {
		this.statDao = statDao;
	}

	@Override
	public List<StatisticsDTO> getCaseStatistics(int id) {
		return statDao.getCaseStatusByBatchLogId(id);
	}
	@Override
	public List<StatisticsDTO> getScenarioStatistics(int id) {
		return statDao.getScenarioStatusByBatchLogId(id);
	}
	
}
