package org.phoenix.web.service;

import java.util.List;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.BatchLogBean;

/**
 * 日志批次操作服务
 * @author mengfeiyang
 *
 */
public interface IBatchLogService {
	/*
	 * 删除一个批次
	 */
	void deleteBatchLog(int id);
	
	/*
	 * 获取日志批次列表，包含分页信息
	 */
	Pager<BatchLogBean> getBatchLogPager(int uid);
	/**
	 * 根据日志的批次Id加载日志
	 * @param id
	 * @return
	 */
	Pager<BatchLogBean> getBatchLogPagerById(int id);
	
	List<BatchLogBean> getBatchLogListByType(String type,int uid);
	
	BatchLogBean getBatchLogBean(int id);
}
