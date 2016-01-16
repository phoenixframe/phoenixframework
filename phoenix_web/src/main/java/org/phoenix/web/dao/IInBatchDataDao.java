package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.model.InterfaceBatchDataBean;

public interface IInBatchDataDao extends IBaseDao<InterfaceBatchDataBean>{
	List<InterfaceBatchDataBean> getInBatchList(int caseId);
	void deleteBatchDataBean(int caseId);
}
