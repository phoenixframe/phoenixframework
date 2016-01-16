package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.BatchLogBean;

/**
 * 日志批次操作接口
 * @author mengfeiyang
 *
 */
public interface IBatchLogDao extends IBaseDao<BatchLogBean>{
	/*
	 * 删除一个批次
	 */
	void deleteBatchLog(int id);
	
	/*
	 * 获取日志批次列表，包含分页信息
	 */
	Pager<BatchLogBean> getBatchLogPager(int uid);
	Pager<BatchLogBean> getBatchLogPagerById(int id);
	
	List<BatchLogBean> getBathLogListByTaskType(String type,int uid);
	
	BatchLogBean getBatchLogBean(int id);
}
