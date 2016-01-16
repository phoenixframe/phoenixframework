package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.CaseBean;
import org.phoenix.web.dao.ICaseDao;
import org.springframework.stereotype.Repository;

@Repository("caseDao")
public class CaseDao extends BaseDao<CaseBean> implements ICaseDao{

	@Override
	public List<CaseBean> getCaseBeanListByUT(int uid, String taskType) {
		return super.list("from CaseBean where userId="+uid+" And caseType='"+taskType+"'");
	}
	
	@Override
	public List<CaseBean> getCaseBeanListByUser(int uid){
		return super.list("from CaseBean where userId="+uid);
	}

	@Override
	public List<CaseBean> getCaseBeanListByScenario(int scenarioId) {
		return super.list("from CaseBean where scenarioBean.id="+scenarioId);
	}

	@Override
	public Pager<CaseBean> getCaseBeanPagerByUser(int uid) {
		return super.find("from CaseBean where userId=?", uid);
	}

	@Override
	public Pager<CaseBean> getCaseBeanPagerByScenario(int scenarioId) {
		return super.find("from CaseBean where scenarioBean.id=?", scenarioId);
	}

	@Override
	public Pager<CaseBean> getCaseBeanPagerByKeyWord(int uid, String keyword,String keyWord2) {
		return super.find("from CaseBean where userId="+uid+" And caseName like '%"+keyword+"%' And scenarioBean.scenarioName like '%"+keyWord2+"%'");
	}

	@Override
	public CaseBean getCaseBeanByName(String name) {
		return (CaseBean) super.queryObject("from CaseBean where caseName='"+name+"'");
	}

}
