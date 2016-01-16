package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.ScenarioBean;
import org.phoenix.web.dao.IScenarioDao;
import org.springframework.stereotype.Repository;

@Repository
public class ScenarioDao extends BaseDao<ScenarioBean> implements IScenarioDao{

	@Override
	public Pager<ScenarioBean> getScenarioBeanPager(int uid) {
		return super.find("from ScenarioBean where userId="+uid);
	}

	@Override
	public List<ScenarioBean> getScenarioBeanList(int uid) {
		return super.list("from ScenarioBean where userId="+uid);
	}

	/*
	 * 删除指定用户下的场数据
	 */
	@Override
	public void deleteByUser(int uid) {
        super.updateByHql("delete ScenarioBean sc where sc.userId=?", uid);		
	}

	@Override
	public Pager<ScenarioBean> getSceanrioBeanPagerBykeyWord(int uid,
			String keyWord) {
		return super.find("from ScenarioBean where userId="+uid+" And scenarioName like '%"+keyWord+"%'");
	}

}
