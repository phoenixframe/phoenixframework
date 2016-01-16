package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.web.dao.ISlaveDao;
import org.phoenix.web.model.SlaveModel;
import org.springframework.stereotype.Repository;

/**
 * 对分机节点操作的dao
 * @author mengfeiyang
 *
 */
@Repository
public class SlaveDao extends BaseDao<SlaveModel> implements ISlaveDao{
	
	/*
	 * 获取分机信息列表，但不含分页信息
	 */
	public List<SlaveModel> getSlaveModelList(int uid){
		return super.list("from SlaveModel where uid="+uid);
	}
	
	/*
	 * 获取分机信息列表，包含分页数据
	 */
	public Pager<SlaveModel> getSlaveModelPager(int uid){
		return super.find("from SlaveModel where uid="+uid);
	}

}
