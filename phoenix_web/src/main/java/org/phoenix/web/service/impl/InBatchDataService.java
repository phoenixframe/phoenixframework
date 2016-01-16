package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.web.dao.IInBatchDataDao;
import org.phoenix.web.service.IInBatchDataService;
import org.springframework.stereotype.Service;

@Service
public class InBatchDataService implements IInBatchDataService{
	private IInBatchDataDao iIBatchDataDao;
	
	public IInBatchDataDao getiIBatchDataDao() {
		return iIBatchDataDao;
	}
	@Resource
	public void setiIBatchDataDao(IInBatchDataDao iIBatchDataDao) {
		this.iIBatchDataDao = iIBatchDataDao;
	}

	@Override
	public InterfaceBatchDataBean addInBatch(InterfaceBatchDataBean dataBean) {
		return iIBatchDataDao.add(dataBean);
	}

	@Override
	public void deleteInBatch(int id) {
		iIBatchDataDao.delete(id);
	}

	@Override
	public void updateInBatch(InterfaceBatchDataBean dataBean) {
		iIBatchDataDao.update(dataBean);
	}

	@Override
	public List<InterfaceBatchDataBean> getInBatchList(int caseId) {
		return iIBatchDataDao.getInBatchList(caseId);
	}
	@Override
	public InterfaceBatchDataBean getInBatchBean(int id) {
		return iIBatchDataDao.load(id);
	}
	@Override
	public void deleteInBatchByCaseId(int caseId) {
		iIBatchDataDao.deleteBatchDataBean(caseId);		
	}

}
