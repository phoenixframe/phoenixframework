package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.InterfaceDataBean;

/**
 * 读取指定的批次数据
 * @author mengfeiyang
 *
 */
public class InterfaceDataDao extends HibernateDaoImpl<InterfaceDataBean> implements IModelDao<InterfaceDataBean>{

	/**
	 * 根据给定的批次的id，读取该批次下的所有数据
	 */
	@Override
	public List<InterfaceDataBean> getModelList(int uid) {
		return super.loadAll("from InterfaceDataBean where interfaceBatchDataBean.id="+uid);
	}
	@Deprecated
	@Override
	public List<InterfaceDataBean> getModelList(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Deprecated
	@Override
	public InterfaceDataBean loadModel(int Id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Deprecated
	@Override
	public InterfaceDataBean loadModel(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
