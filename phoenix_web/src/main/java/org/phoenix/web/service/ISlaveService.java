package org.phoenix.web.service;

import java.util.List;

import org.phoenix.basic.paging.Pager;
import org.phoenix.web.model.SlaveModel;

/**
 * 分机信息操作服务接口
 * @author mengfeiyang
 *
 */
public interface ISlaveService {
	/*
	 * 获取分机列表，不含分页数据
	 */
	List<SlaveModel> getSlaveModelList(int uid);
	
	/*
	 * 获取分机列表，包含分页数据
	 */
	Pager<SlaveModel> getSlaveModelPager(int uid);
	
	/*
	 * 增加一条记录
	 */
	void add(SlaveModel slaveModel);
	
	/*
	 * 删除一条记录
	 */
	void delete(int id);
	
	/*
	 * 更新一条记录
	 */
	void update(SlaveModel slaveModel);
	
	/*
	 * 根据id获取一条记录
	 */
	SlaveModel getModel(int id);

}
