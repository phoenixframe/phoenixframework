package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.BatchLogBean;

public class BatchLogDao extends HibernateDaoImpl<BatchLogBean> implements IModelDao<BatchLogBean>{

	@Override
	public List<BatchLogBean> getModelList(int uid) {
		return super.loadAll("from BatchLogBean where uid="+uid);
	}

	/*
	 * 此方法在此类中不建议被使用
	 * (non-Javadoc)
	 * @see org.phoenix.dao.IModelDao#getModelList(java.lang.String)
	 */
	@Override
	public List<BatchLogBean> getModelList(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BatchLogBean loadModel(int Id) {
		return super.load(Id);
	}

	@Override
	public BatchLogBean loadModel(String name) {
		return super.load("from BatchLogBean where batchId="+name);
	}
	
	public void addModel(BatchLogBean batchLogBean){
		super.add(batchLogBean);
	}

}
