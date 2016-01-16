package org.phoenix.web.dao.impl;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.PerfBatchLogModel;
import org.phoenix.web.dao.IPerfBatchDao;
import org.springframework.stereotype.Repository;

@Repository
public class PerfBatchDao extends BaseDao<PerfBatchLogModel> implements IPerfBatchDao{

	@Override
	public Pager<PerfBatchLogModel> loadPerfBatchPager(int perfCaseId) {
		return super.find("from PerfBatchLogModel where phoenixJmeterBean.id="+perfCaseId+" order by id desc");
	}

}
