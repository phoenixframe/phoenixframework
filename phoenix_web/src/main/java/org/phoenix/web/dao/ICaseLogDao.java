package org.phoenix.web.dao;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.CaseLogBean;

/**
 * 用例日志操作接口
 * @author mengfeiyang
 *
 */
public interface ICaseLogDao extends IBaseDao<CaseLogBean>{
	/*
	 * 删除一条记录
	 */
     void deleteCaseLog(int id);
     
     /*
      * 获取一条记录
      */
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
