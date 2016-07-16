package org.phoenix.api.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.phoenix.api.ICommonAPI;
import org.phoenix.dao.InterfaceBatchDataDao;
import org.phoenix.dao.InterfaceDataDao;
import org.phoenix.dao.LocatorDao;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.InterfaceDataBean;
import org.phoenix.model.LocatorBean;
import org.phoenix.model.UnitLogBean;

/**
 * 通用操作实现类
 * @author mengfeiyang
 *
 */
public class CommonAPI implements ICommonAPI{

	private LocatorDao locatorDao = new LocatorDao();
	private InterfaceBatchDataDao ibatchDao = new InterfaceBatchDataDao();
	private InterfaceDataDao idataDao = new InterfaceDataDao();
	private HashMap<String,LocatorBean> locators;
	private LinkedList<UnitLogBean> unitLog;
	private CaseLogBean caseLogBean;
	public CommonAPI() {
		// TODO Auto-generated constructor stub
	}
	
	public CommonAPI(LinkedList<UnitLogBean> unitLog,CaseLogBean caseLogBean){
		this.unitLog = unitLog;
		this.caseLogBean = caseLogBean;
	}
	
	public HashMap<String, LocatorBean> getLocators() {
		return locators;
	}

	public void setLocators(HashMap<String, LocatorBean> locators) {
		this.locators = locators;
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#loadWebCaseDatas(int)
	 */
	@Override
	public HashMap<InterfaceBatchDataBean,HashMap<String,String>> loadWebCaseDatas(int caseId){
		HashMap<InterfaceBatchDataBean,HashMap<String,String>> webDatas = new HashMap<InterfaceBatchDataBean,HashMap<String,String>>();
		LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> datas = this.loadInterfaceDatas(caseId);
		for(Entry<InterfaceBatchDataBean,List<InterfaceDataBean>> data : datas.entrySet()){
			HashMap<String,String> dataBlocks = new HashMap<String,String>();
			for(InterfaceDataBean idata : data.getValue()){
				dataBlocks.put(idata.getDataName(), idata.getDataContent());
			}
			webDatas.put(data.getKey(), dataBlocks);
		}
		return webDatas;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#loadWebCaseDatas(java.lang.String)
	 */
	@Override
	public HashMap<InterfaceBatchDataBean,HashMap<String,String>> loadWebCaseDatas(String caseName){
		HashMap<InterfaceBatchDataBean,HashMap<String,String>> webDatas = new HashMap<InterfaceBatchDataBean,HashMap<String,String>>();
		LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> datas = this.loadInterfaceDatas(caseName);
		for(Entry<InterfaceBatchDataBean,List<InterfaceDataBean>> data : datas.entrySet()){
			HashMap<String,String> dataBlocks = new HashMap<String,String>();
			for(InterfaceDataBean idata : data.getValue()){
				dataBlocks.put(idata.getDataName(), idata.getDataContent());
			}
			webDatas.put(data.getKey(), dataBlocks);
		}
		return webDatas;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#addLocator(int, org.phoenix.model.CaseLogBean)
	 */
	@Override
	public HashMap<String,LocatorBean> addLocator(int caseId){
		locators = new HashMap<String,LocatorBean>();
		List<LocatorBean> llist = locatorDao.getModelList(caseId);
		for(LocatorBean locatorBean : llist){
			locators.put(locatorBean.getLocatorDataName(), locatorBean);
		}
		setLocators(locators);
		return locators;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#addLocator(java.lang.String, org.phoenix.model.CaseLogBean)
	 */
	@Override
	public HashMap<String,LocatorBean> addLocator(String caseName){
		locators = new HashMap<String,LocatorBean>();
		List<LocatorBean> list = locatorDao.getModelList(caseName);
		for(LocatorBean locatorBean : list){
			locators.put(locatorBean.getLocatorDataName(), locatorBean);
		}
		setLocators(locators);
		return locators;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#loadInterfaceDatas(int)
	 */
	@Override
	public LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> loadInterfaceDatas(int caseId){
		List<InterfaceBatchDataBean> iBatchList = ibatchDao.getModelList(caseId);
		LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> iBatchDataMap = new LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>>();
		for(InterfaceBatchDataBean iBatch : iBatchList){
			iBatchDataMap.put(iBatch, idataDao.getModelList(iBatch.getId()));
		}
		return iBatchDataMap;
	}
	/**
	 * 根据给定的用例的name，获取该用例下所有的数据
	 */
	@Override
	public LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> loadInterfaceDatas(String caseName){
		List<InterfaceBatchDataBean> iBatchList = ibatchDao.getModelList(caseName);
		LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> iBatchDataMap = new LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>>();
		for(InterfaceBatchDataBean iBatch : iBatchList){
			iBatchDataMap.put(iBatch, idataDao.getModelList(iBatch.getId()));
		}
		return iBatchDataMap;
	}
	/**
	 * 添加聚合用例。根据用例的名称<br>
	 * 聚合用例用于执行当前用例中嵌入的外部用例,仅支持一级调用
	 */
	@Override
	public void addAggregateCase(String caseName){
		
	}
	/**
	 * 添加聚合用例。根据用例的id<br>
	 * 聚合用例用于执行当前用例中嵌入的外部用例,仅支持一级调用
	 */
	@Override
	public void addAggregateCase(int caseId){
		
	}
	/**
	 * Thread.sleep(t);
	 */
	@Override
	public void sleep(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.ICommonAPI#addLog(java.lang.String)
	 */
	@Override
	public void addLog(String log) {
		unitLog.add(new UnitLogBean("自定义步骤 [ addLog ] 执行成功，参数值：[ "+log+" ]", "addLog", "STEP","SUCCESS", "", caseLogBean));
	}

}
