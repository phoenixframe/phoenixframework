package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.web.dao.ISlaveDao;
import org.phoenix.web.model.SlaveModel;
import org.phoenix.web.service.ISlaveService;
import org.springframework.stereotype.Service;

/**
 * 分机节点操作服务
 * @author mengfeiyang
 *
 */
@Service
public class SlaveService implements ISlaveService{
	private ISlaveDao slaveDao;

	public ISlaveDao getSlaveDao() {
		return slaveDao;
	}
    @Resource
	public void setSlaveDao(ISlaveDao slaveDao) {
		this.slaveDao = slaveDao;
	}

    /*
     * (non-Javadoc)
     * @see org.phoenix.web.service.ISlaveService#getSlaveModelList()
     */
	@Override
	public List<SlaveModel> getSlaveModelList(int uid) {
		return slaveDao.getSlaveModelList(uid);
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.web.service.ISlaveService#getSlaveModelPager()
	 */
	@Override
	public Pager<SlaveModel> getSlaveModelPager(int uid) {
		return slaveDao.getSlaveModelPager(uid);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.web.service.ISlaveService#add(org.phoenix.web.model.SlaveModel)
	 */
	@Override
	public void add(SlaveModel slaveModel) {
		slaveDao.add(slaveModel);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.web.service.ISlaveService#delete(int)
	 */
	@Override
	public void delete(int id) {
		slaveDao.delete(id);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.web.service.ISlaveService#update(org.phoenix.web.model.SlaveModel)
	 */
	@Override
	public void update(SlaveModel slaveModel) {
		slaveDao.update(slaveModel);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.web.service.ISlaveService#getModel(int)
	 */
	@Override
	public SlaveModel getModel(int id) {
		return slaveDao.load(id);
	}

}
