package org.phoenix.web.service;

import java.util.List;

import org.phoenix.model.InterfaceBatchDataBean;

public interface IInBatchDataService {
	InterfaceBatchDataBean addInBatch(InterfaceBatchDataBean dataBean);
	void deleteInBatch(int id);
	void deleteInBatchByCaseId(int caseId);
	void updateInBatch(InterfaceBatchDataBean dataBean);
	InterfaceBatchDataBean getInBatchBean(int id);
	List<InterfaceBatchDataBean> getInBatchList(int caseId);
}
