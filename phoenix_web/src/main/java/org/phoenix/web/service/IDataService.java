package org.phoenix.web.service;

import java.util.List;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.DataBean;

public interface IDataService {
	DataBean addData(DataBean dataBean);
	void addDataBeanList(List<DataBean> list);
	void deleData(int id);
	void updateData(DataBean dataBean);
	DataBean getData(int id);
	Pager<DataBean> getDataPager(int caseId);
}
