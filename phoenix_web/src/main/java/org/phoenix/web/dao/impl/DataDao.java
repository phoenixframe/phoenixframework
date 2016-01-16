package org.phoenix.web.dao.impl;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.DataBean;
import org.phoenix.web.dao.IDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class DataDao extends BaseDao<DataBean> implements IDataDao{

	@Override
	public Pager<DataBean> getDataPager(int caseId) {
		return super.find("from DataBean d where d.caseBean.id="+caseId);
	}

}
