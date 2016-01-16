package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.web.dao.IInBatchDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class InBatchDataDao extends BaseDao<InterfaceBatchDataBean> implements IInBatchDataDao{

	@Override
	public List<InterfaceBatchDataBean> getInBatchList(int caseId) {
		return super.list("from InterfaceBatchDataBean where caseBean.id="+caseId);
	}

	@Override
	public void deleteBatchDataBean(int caseId) {
		super.updateByHql("delete from InterfaceBatchDataBean where caseBean.id="+caseId);
	}

}
