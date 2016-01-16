package org.phoenix.web.service.impl;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.web.dao.IAttachDao;
import org.phoenix.web.model.AttachModel;
import org.phoenix.web.service.IAttachService;
import org.springframework.stereotype.Service;

@Service
public class AttachService implements IAttachService{
	private IAttachDao attachDao;
	public IAttachDao getAttachDao() {
		return attachDao;
	}
	@Resource
	public void setAttachDao(IAttachDao attachDao) {
		this.attachDao = attachDao;
	}

	@Override
	public Pager<AttachModel> getAttachPager(int uid) {
		return attachDao.getAttachPager(uid);
	}

	@Override
	public void deleteAttach(int id) {
		attachDao.delete(id);
	}
	@Override
	public AttachModel add(AttachModel attachModel) {
		return attachDao.add(attachModel);
	}
	@Override
	public AttachModel getAttachModel(int id) {
		return attachDao.load(id);
	}
	@Override
	public Pager<AttachModel> getAttachPagerByKeyWord(int uid, String keyWord) {
		return attachDao.getAttachPagerByKeyWord(uid, keyWord);
	}

}
