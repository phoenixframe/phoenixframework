package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.model.InterfaceBatchDataBean;
/**
 * 日志批次操作接口
 * @author mengfeiyang
 *
 */
public interface IInBatchDataDao extends IBaseDao<InterfaceBatchDataBean>{
	List<InterfaceBatchDataBean> getInBatchList(int caseId);
	void deleteBatchDataBean(int caseId);
}
