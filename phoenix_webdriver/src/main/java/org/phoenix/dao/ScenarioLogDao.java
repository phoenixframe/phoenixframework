package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.ScenarioLogBean;

public class ScenarioLogDao extends HibernateDaoImpl<ScenarioLogBean> implements IModelDao<ScenarioLogBean>{

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.dao.IModelDao#getModelList(int)
	 */
	@Override
	public List<ScenarioLogBean> getModelList(int uid) {
		return super.loadAll("from ScenarioLogBean s where s.batchLogBean.id="+uid);
	}

	@Override
	public List<ScenarioLogBean> getModelList(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScenarioLogBean loadModel(int Id) {
		return super.load(Id);
	}

	@Override
	public ScenarioLogBean loadModel(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ScenarioLogBean addModel(ScenarioLogBean scenarioLogBean){
		return super.add(scenarioLogBean);
	}

}
