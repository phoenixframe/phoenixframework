package org.phoenix.api;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.InterfaceDataBean;
import org.phoenix.model.LocatorBean;

/**
 * 通用操作接口
 * @author mengfeiyang
 *
 */
public interface ICommonAPI {
	/**
	 * 加载web用例的数据，根据用例的名称
	 * @param caseName
	 * @return
	 */
	HashMap<InterfaceBatchDataBean,HashMap<String,String>> loadWebCaseDatas(String caseName);
	
	/**
	 * 加载web用例的数据，根据用例的id
	 * @param caseId
	 * @return
	 */
	HashMap<InterfaceBatchDataBean,HashMap<String,String>> loadWebCaseDatas(int caseId);
	/**
	 * 加载数据与定位信息
	 * @param caseId
	 * @param caseLogBean
	 */
	HashMap<String,LocatorBean> addLocator(int caseId);
	/**
	 * 加载数据与定位信息
	 * @param caseName
	 * @param caseLogBean
	 */
	HashMap<String,LocatorBean> addLocator(String caseName);
	/**
	 * 根据给定的用例的id，获取该用例下所有的数据
	 * @param caseId
	 */
	LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> loadInterfaceDatas(int caseId);
	
	/**
	 * 根据给定的用例的name，获取该用例下所有的数据
	 * @param caseName
	 */
	LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> loadInterfaceDatas(String caseName);
	/**
	 * 根据用例的名称添加聚合用例
	 * @return
	 */
	void addAggregateCase(String caseName);
	/**
	 * 根据用例的id添加聚合用例
	 * @param caseId
	 */
	void addAggregateCase(int caseId);
	/**
	 * 线程睡眠
	 * @param t
	 */
	void sleep(long t);
	/**
	 * 支持自定义日志
	 * @param log
	 * @return
	 */
	void addLog(String log);
}
