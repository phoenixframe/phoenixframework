package org.phoenix.web.service;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.CaseLogBean;

public interface ICaseLogService {
	void deleteCaseLog(int id);
	CaseLogBean getCaseLog(int id);
    /*
     * 根据日志的批次获取所有日志，包含分页信息
     */
    Pager<CaseLogBean> getCaseLogPagerByBatchLog(int batchLogId);
   
    /*
     * 根据场景id获取日志记录，包括分页信息
     */
    Pager<CaseLogBean> getCaseLogPagerByScenarioLog(int scenarioLogId);
}
