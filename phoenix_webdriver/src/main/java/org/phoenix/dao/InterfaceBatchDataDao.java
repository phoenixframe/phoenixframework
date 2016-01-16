package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.InterfaceBatchDataBean;

/**
 * 接口测试时，数据批次操作
 * @author mengfeiyang
 *
 */
public class InterfaceBatchDataDao extends HibernateDaoImpl<InterfaceBatchDataBean> implements IModelDao<InterfaceBatchDataBean>{

	/**
	 * 根据给予的用例的id读取关联的所有批次
	 */
	@Override
	public List<InterfaceBatchDataBean> getModelList(int uid) {
		return super.loadAll("from InterfaceBatchDataBean where caseBean.id="+uid);
	}

	/**
	 * 根据给予的用例的名称读取关联的所有批次
	 */
	@Override
	public List<InterfaceBatchDataBean> getModelList(String name) {
		return super.loadAll("from InterfaceBatchDataBean where caseBean.caseName='"+name+"'");
	}

	@Deprecated
	@Override
	public InterfaceBatchDataBean loadModel(int Id) {
		// TODO Auto-generated method stub
		return null;
	}
    @Deprecated
	@Override
	public InterfaceBatchDataBean loadModel(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
