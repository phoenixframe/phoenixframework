package org.phoenix.web.service;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.PerfBatchLogModel;

public interface IPerfBatchLogService {
	PerfBatchLogModel load(int batchId);
	void deleteBatchLog(int batchId);
	Pager<PerfBatchLogModel> getBatchLogPager(int perfCaseId);
}
