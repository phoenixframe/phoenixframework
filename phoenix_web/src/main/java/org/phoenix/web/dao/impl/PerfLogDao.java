package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.model.PerfLogModel;
import org.phoenix.web.dao.IPerfLogDao;
import org.springframework.stereotype.Repository;

@Repository
public class PerfLogDao extends BaseDao<PerfLogModel> implements IPerfLogDao{
	@Override
	public List<PerfLogModel> loadPerfLogModels(int perfCaseId) {
		return super.list("from PerfLogModel where perfBatchLogModel.id="+perfCaseId);
	}

}
