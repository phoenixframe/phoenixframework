package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.LocatorBean;

/**
 * 定位信息服务接口
 * @author mengfeiyang
 *
 */
public interface ILocatorDao extends IBaseDao<LocatorBean>{
	/*
	 * 获取定位信息列表，根据用例的id
	 */
	List<LocatorBean> getLocatorBeanListByCase(int caseId);
	
	/*
	 * 获取定位信息的列表，包括分页信息
	 */
	Pager<LocatorBean> getLocatorBeanListByPager(int caseId);
}
