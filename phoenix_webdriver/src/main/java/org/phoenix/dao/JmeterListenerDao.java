package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.dao.IModelDao;
import org.phoenix.model.JmeterListenerBean;

public class JmeterListenerDao extends HibernateDaoImpl<JmeterListenerBean> implements IModelDao<JmeterListenerBean>{

	@Override
	public List<JmeterListenerBean> getModelList(int uid) {
		return loadAll("from JmeterListenerBean where perfCaseId="+uid);
	}
	@Deprecated
	@Override
	public List<JmeterListenerBean> getModelList(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JmeterListenerBean loadModel(int Id) {
		return load("from JmeterListenerBean where id="+Id);
	}

	@Deprecated
	@Override
	public JmeterListenerBean loadModel(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
