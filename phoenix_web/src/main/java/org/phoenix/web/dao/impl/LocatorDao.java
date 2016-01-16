package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.LocatorBean;
import org.phoenix.web.dao.ILocatorDao;
import org.springframework.stereotype.Repository;

/**
 * 根据对应用例下的定位信息
 * @author mengfeiyang
 *
 */
@Repository
public class LocatorDao extends BaseDao<LocatorBean> implements ILocatorDao{

	/*
	 * 获取定位信息列
	 */
	@Override
	public List<LocatorBean> getLocatorBeanListByCase(int caseId) {
		return super.list("from LocatorBean where caseBean.id="+caseId);
	}

	/*
	 * 获取定位信息列表及分页信息
	 */
	@Override
	public Pager<LocatorBean> getLocatorBeanListByPager(int caseId) {
		return super.find("from LocatorBean where caseBean.id="+caseId);
	}

}
