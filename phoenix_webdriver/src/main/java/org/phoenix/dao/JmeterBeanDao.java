package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.PhoenixJmeterBean;

public class JmeterBeanDao extends HibernateDaoImpl<PhoenixJmeterBean> implements IModelDao<PhoenixJmeterBean>{

	@Deprecated
	@Override
	public List<PhoenixJmeterBean> getModelList(int uid) {
		return null;
	}
	@Deprecated
	@Override
	public List<PhoenixJmeterBean> getModelList(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhoenixJmeterBean loadModel(int Id) {
		return load(Id);
	}
	@Deprecated
	@Override
	public PhoenixJmeterBean loadModel(String name) {
		return null;
	}


}
