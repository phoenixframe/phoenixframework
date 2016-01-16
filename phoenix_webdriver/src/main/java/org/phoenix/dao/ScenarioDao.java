package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.ScenarioBean;

/**
 * 操作场景的dao
 * @author mengfeiyang
 *
 */
public class ScenarioDao extends HibernateDaoImpl<ScenarioBean> implements IModelDao<ScenarioBean>{

	/**
	 * 根据用户的id，加载该用户名下的测试场景列表
	 */
	@Override
	public List<ScenarioBean> getModelList(int uid) {
		return super.loadAll("from ScenarioBean where userId="+uid);
	}

	/**
	 * 该方法已停用，返回的结果为null
	 */
	@Override
	public List<ScenarioBean> getModelList(String name) {
		return null;
	}

	/**
	 * 根据场景的id，加载一条场景记录
	 */
	@Override
	public ScenarioBean loadModel(int Id) {
		return load(Id);
	}

	/**
	 * 根据场景的完全名称，加载一条场景记录
	 */
	@Override
	public ScenarioBean loadModel(String name) {
		return super.load("from ScenarioBean where scenarioName="+name);
	}

}
