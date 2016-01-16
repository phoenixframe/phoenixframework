package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.ScenarioBean;

/**
 * 场景数据服务接口
 * @author mengfeiyang
 *
 */
public interface IScenarioDao extends IBaseDao<ScenarioBean>{
       
	/*
	 * 获取多批及分页信息
	 */
	Pager<ScenarioBean> getScenarioBeanPager(int uid);
	
	Pager<ScenarioBean> getSceanrioBeanPagerBykeyWord(int uid,String keyWord);
	
	/*
	 * 获取当前用户下的所有
	 */
	List<ScenarioBean> getScenarioBeanList(int uid);

	/*
	 * 删除指定用户下的场景
	 */
	void deleteByUser(int uid);
}
