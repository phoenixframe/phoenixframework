package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.PhoenixJmeterBean;
import org.phoenix.web.dao.IPerformanceDao;
import org.springframework.stereotype.Repository;

@Repository
public class PerformanceDao extends BaseDao<PhoenixJmeterBean> implements IPerformanceDao{

	@Override
	public List<PhoenixJmeterBean> getJmeterBeansByUid(int uid) {
		return super.list("from PhoenixJmeterBean where userId="+uid);
	}

	@Override
	public Pager<PhoenixJmeterBean> getJmeterBeansPagerByUid(int uid) {
		return super.find("from PhoenixJmeterBean where userId="+uid+" order by id desc");
	}

	@Override
	public Pager<PhoenixJmeterBean> getJmeterBeansPagerByKeyWord(int uid,String keyWord) {
		return super.find("from PhoenixJmeterBean where userId='"+uid+"' and caseName like '%"+keyWord+"%'");
	}

}
