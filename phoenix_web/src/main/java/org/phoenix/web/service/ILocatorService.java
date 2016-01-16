package org.phoenix.web.service;

import java.util.List;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.LocatorBean;

/**
 * 对用例信息的服务
 * @author mengfeiyang
 *
 */
public interface ILocatorService {
	/*
	 * 增加
	 */
	void addLocator(LocatorBean locatorBean);
	
	/*
	 * 删除
	 */
	void delLocator(int id);
	void updateLocator(LocatorBean locatorBean);
	
	/*
	 * 获取一条记录
	 */
	LocatorBean getLocatorBean(int id);
	
	/*
	 * 获取记录列表，根据用例的id
	 */
	List<LocatorBean> getLocatorBeanList(int caseId);
	
	void addLocatorBeanList(List<LocatorBean> list);
	
	/*
	 * 获取记录的列表及分页信息，根据用例id
	 */
	Pager<LocatorBean> getLoatorBeanPager(int caseId);
}
