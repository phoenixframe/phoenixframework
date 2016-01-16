package org.phoenix.web.service;

import java.util.List;

import org.phoenix.model.PerfLogModel;

public interface IPerfLogService {
	List<PerfLogModel> loadPerfLogModels(int perfCaseId);
}
