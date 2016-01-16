package org.phoenix.web.service;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.ScenarioLogBean;

public interface IScenarioLogService {
	void deleteLog(int id);
	ScenarioLogBean getLog(int id);
	Pager<ScenarioLogBean> getLogPager(int batchLogId);
}
