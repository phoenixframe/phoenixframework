package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.DataBean;
import org.phoenix.web.dao.IDataDao;
import org.phoenix.web.service.IDataService;
import org.springframework.stereotype.Service;

@Service
public class DataService implements IDataService{
	private IDataDao dataDao;
	public IDataDao getDataDao() {
		return dataDao;
	}
	@Resource
	public void setDataDao(IDataDao dataDao) {
		this.dataDao = dataDao;
	}

	@Override
	public DataBean addData(DataBean dataBean) {
		return dataDao.add(dataBean);
	}

	@Override
	public void deleData(int id) {
		dataDao.delete(id);
	}

	@Override
	public void updateData(DataBean dataBean) {
		dataDao.update(dataBean);
	}

	@Override
	public DataBean getData(int id) {
		return dataDao.load(id);
	}

	@Override
	public Pager<DataBean> getDataPager(int caseId) {
		return dataDao.getDataPager(caseId);
	}
	@Override
	public void addDataBeanList(List<DataBean> list) {
		dataDao.addBatchData(list);
	}

}
