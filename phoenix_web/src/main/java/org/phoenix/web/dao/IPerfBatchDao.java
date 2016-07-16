package org.phoenix.web.dao;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.PerfBatchLogModel;
/**
 * 性能测试日志操作接口
 * @author mengfeiyang
 *
 */
public interface IPerfBatchDao extends IBaseDao<PerfBatchLogModel>{
	Pager<PerfBatchLogModel> loadPerfBatchPager(int perfCaseId);
}
