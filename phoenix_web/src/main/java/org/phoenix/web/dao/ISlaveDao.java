package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.web.model.SlaveModel;

/**
 * 对分机控制的dao
 * @author mengfeiyang
 *
 */
public interface ISlaveDao extends IBaseDao<SlaveModel>{
	/*
	 * 获取分机信息列表，不含分页数据
	 */
	List<SlaveModel> getSlaveModelList(int uid);
	
	/*
	 * 获取分机信息列表，包含分页数据
	 */
	Pager<SlaveModel> getSlaveModelPager(int uid);
}
