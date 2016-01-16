package org.phoenix.web.service;

import java.util.List;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.CaseBean;

public interface ICaseService {
	
	void addCase(CaseBean caseBean);
	void delCase(int id);
	void updateCase(CaseBean caseBean);
	List<CaseBean> getCaseBeanListByUser(int uid);
	List<CaseBean> getCaseBeanListByUT(int uid,String taskType);
	List<CaseBean> getCaseBeanListByScenario(int scenarioId);
	Pager<CaseBean> getCaseBeanPagerByUser(int uid);
	Pager<CaseBean> getCaseBeanPagerByScenario(int scenarioId);
	Pager<CaseBean> getCaseBeanPagerByKeyWord(int uid,String keyword,String keyword2);
	CaseBean getCaseBean(int id);
	CaseBean getCaseBeanByName(String name);

}
