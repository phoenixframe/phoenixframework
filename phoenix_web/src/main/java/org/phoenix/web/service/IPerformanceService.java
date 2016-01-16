package org.phoenix.web.service;

import java.util.List;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.PhoenixJmeterBean;

public interface IPerformanceService {
	PhoenixJmeterBean add(PhoenixJmeterBean jmeterBean);
	void delete(int id);
	void update(PhoenixJmeterBean jmeterBean);
	PhoenixJmeterBean getJmeterBean(int id);
	List<PhoenixJmeterBean> getJmeterBeanList(int uid);
	Pager<PhoenixJmeterBean> getJmeterBeanPager(int uid);
	Pager<PhoenixJmeterBean> getJmeterBeansPagerByKeyWord(int uid,String keyWord);
}
