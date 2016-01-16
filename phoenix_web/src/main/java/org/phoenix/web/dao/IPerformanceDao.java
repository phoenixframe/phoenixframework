package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.PhoenixJmeterBean;

public interface IPerformanceDao extends IBaseDao<PhoenixJmeterBean>{
	List<PhoenixJmeterBean> getJmeterBeansByUid(int uid);
	Pager<PhoenixJmeterBean> getJmeterBeansPagerByUid(int uid);
	Pager<PhoenixJmeterBean> getJmeterBeansPagerByKeyWord(int uid,String keyWord);
}
