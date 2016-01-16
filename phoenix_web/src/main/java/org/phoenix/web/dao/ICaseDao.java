package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.CaseBean;

/**
 * 用例表操作接口
 * @author mengfeiyang
 *
 */
public interface ICaseDao extends IBaseDao<CaseBean>{
	/*
	 * 获取指定用户下的用例数据
	 */
     List<CaseBean> getCaseBeanListByUser(int uid);
     List<CaseBean> getCaseBeanListByUT(int uid,String taskType);
     List<CaseBean> getCaseBeanListByScenario(int scenarioId);
     Pager<CaseBean> getCaseBeanPagerByUser(int uid);
     Pager<CaseBean> getCaseBeanPagerByScenario(int scenarioId);
     Pager<CaseBean> getCaseBeanPagerByKeyWord(int uid,String keyword,String keyWord2);
     CaseBean getCaseBeanByName(String name);
}
