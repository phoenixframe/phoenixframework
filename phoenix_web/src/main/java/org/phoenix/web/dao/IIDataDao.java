package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.model.InterfaceDataBean;
/**
 * 测试数据操作接口
 * @author mengfeiyang
 *
 */
public interface IIDataDao extends IBaseDao<InterfaceDataBean>{
	List<InterfaceDataBean> getDataBeans(int batchId);
}	
