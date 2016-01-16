package org.phoenix.web.dao;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.DataBean;

/**
 * 
 * @author mengfeiyang
 *
 */
public interface IDataDao extends IBaseDao<DataBean>{
	Pager<DataBean> getDataPager(int caseId);

}
