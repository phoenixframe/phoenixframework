package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.model.InterfaceDataBean;
import org.phoenix.web.dao.IIDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class InDataDao extends BaseDao<InterfaceDataBean> implements IIDataDao{

	@Override
	public List<InterfaceDataBean> getDataBeans(int batchId) {
		return super.list("from InterfaceDataBean where interfaceBatchDataBean.id="+batchId);
	}

}
