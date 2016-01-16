package org.phoenix.web.service;

import java.util.List;

import org.phoenix.model.InterfaceDataBean;

public interface IInDataBeanService {
	InterfaceDataBean addDataBean(InterfaceDataBean inDataBean);
	void addDataBeans(List<InterfaceDataBean> dataBeans);
	void deleteDataBean(int id);
	void updateDataBean(InterfaceDataBean inDataBean);
	InterfaceDataBean getDataBean(int id);
	List<InterfaceDataBean> getDataBeans(int batchId);
}
