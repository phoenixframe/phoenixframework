package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.CaseBean;

/**
 * 用例操作dao
 * @author mengfeiyang
 *
 */
public class CaseDao extends HibernateDaoImpl<CaseBean> implements IModelDao<CaseBean>{

	/**
	 * id为场景的id。
	 * 根据场景id，获取该场景下的所有用例列表
	 */
	@Override
	public List<CaseBean> getModelList(int Id) {
		return super.loadAll("from CaseBean c where c.scenarioBean.id="+Id);
	}

	/**
	 * 根据用例的id，获取一条用例记录
	 */
	@Override
	public CaseBean loadModel(int Id) {
		
		return super.load("from CaseBean where id="+Id);
	}

	/**
	 * 根据用例的完整名称，获取一条用例记录
	 */
	@Override
	public CaseBean loadModel(String name) {
		return super.load("from CaseBean where caseName='"+name+"'");
	}

	/**
	 * 参数name为场景的名称
	 * 根据场景的名称，获取该场景下的所有用例列表
	 */
	@Override
	public List<CaseBean> getModelList(String name) {
		return super.loadAll("from CaseBean c where c.scenarioBean.caseName="+name);
	}
}
