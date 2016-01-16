package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.model.InterfaceDataBean;
import org.phoenix.web.dao.IIDataDao;
import org.phoenix.web.service.IInDataBeanService;
import org.springframework.stereotype.Service;

@Service
public class InDataBeanService implements IInDataBeanService{
	private IIDataDao iIDataDao;
	
	public IIDataDao getiIDataDao() {
		return iIDataDao;
	}
	@Resource
	public void setiIDataDao(IIDataDao iIDataDao) {
		this.iIDataDao = iIDataDao;
	}

	@Override
	public InterfaceDataBean addDataBean(InterfaceDataBean inDataBean) {
		return iIDataDao.add(inDataBean);
	}

	@Override
	public void addDataBeans(List<InterfaceDataBean> dataBeans) {
		iIDataDao.addBatchData(dataBeans);
	}

	@Override
	public void deleteDataBean(int id) {
		iIDataDao.delete(id);
	}

	@Override
	public void updateDataBean(InterfaceDataBean inDataBean) {
		iIDataDao.update(inDataBean);
	}

	@Override
	public InterfaceDataBean getDataBean(int id) {
		return iIDataDao.load(id);
	}

	@Override
	public List<InterfaceDataBean> getDataBeans(int batchId) {
		return iIDataDao.getDataBeans(batchId);
	}

}
