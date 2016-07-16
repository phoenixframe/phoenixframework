package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.model.PerfLogModel;
/**
 * 性能测试日志操作接口
 * @author mengfeiyang
 *
 */
public interface IPerfLogDao extends IBaseDao<PerfLogModel>{
	List<PerfLogModel> loadPerfLogModels(int perfCaseId);
}
