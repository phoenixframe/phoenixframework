package org.phoenix.dao;

import java.util.List;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.LocatorBean;

/**
 * 加载定位信息的实现类
 * @author mengfeiyang
 *
 */
public class LocatorDao extends HibernateDaoImpl<LocatorBean> implements IModelDao<LocatorBean>{
	
	/**
	 * 参数id为用例的id，
	 * 根据用例的id值获取该用例下的所有定位信息列表
	 */
	@Override
	public List<LocatorBean> getModelList(int caseId) {
		return super.loadAll("from LocatorBean l where l.caseBean.id="+caseId);
	}
	
	/**
	 * 参数caseName为用例的caseName，
	 * 根据用例的名称值获取该用例下的所有定位信息列表
	 */
	@Override
	public List<LocatorBean> getModelList(String caseName) {
		return super.loadAll("from LocatorBean l where l.caseBean.caseName='"+caseName+"'");
	}
	
	/**
	 * 根据定位信息的id，加载一条记录
	 */
	@Override
	public LocatorBean loadModel(int unitId) {
		return super.load(unitId);
	}


	/**
	 * 根据定位信息的name，加载一条记录
	 */
	@Override
	public LocatorBean loadModel(String name) {
		return super.load("from LocatorBean where locatorDataName="+name);
	}
	/**
	 * 根据用例id，和定位信息的名称加载一条定位信息的记录
	 * @param locatorName
	 * @param caseId
	 * @return
	 */
	public LocatorBean loadModel(String locatorName,String caseId){
		return super.load("from LocatorBean l where l.locatorDataName="+locatorName+" and l.caseBean.id="+caseId);
	}

}
