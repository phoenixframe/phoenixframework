package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.DataBean;
/**
 * 加载数据
 * @author mengfeiyang
 *
 */
public class DataDao extends HibernateDaoImpl<DataBean> implements IModelDao<DataBean>{

	@Override
	public List<DataBean> getModelList(int uid) {
		return super.loadAll("from DataBean d where d.caseBean.id="+uid);
	}

	@Override
	public List<DataBean> getModelList(String name) {
		return super.loadAll("from DataBean d where d.caseBean.caseName='"+name+"'");
	}

	@Override
	public DataBean loadModel(int Id) {
		return super.load(Id);
	}

	@Override
	public DataBean loadModel(String name) {
		return super.load("from DataBean where dataName='"+name+"'");
	}

}
