package org.phoenix.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.MsgBean;
import org.phoenix.web.dao.IMsgDao;
import org.phoenix.web.service.IMsgService;
import org.springframework.stereotype.Service;

@Service
public class MsgService implements IMsgService{
	private IMsgDao msgDao;

	public IMsgDao getMsgDao() {
		return msgDao;
	}
	@Resource
	public void setMsgDao(IMsgDao msgDao) {
		this.msgDao = msgDao;
	}
	@Override
	public void deleteMsg(int id) {
		msgDao.delete(id);
	}
	@Override
	public MsgBean getMsgBean(int id) {
		return msgDao.load(id);
	}
	@Override
	public Pager<MsgBean> getMsgBeanPager(int uid) {
		return msgDao.getMsgBeanPager(uid);
	}
	@Override
	public Pager<MsgBean> getMsgBeanPagerByStatus(String msgStatusType,int uid) {
		return msgDao.getMsgBeanPagerByStatus(msgStatusType, uid);
	}
	@Override
	public List<MsgBean> getMsgBeanListForEmail() {
		return msgDao.getMsgBeanListForEmail();
	}
	@Override
	public void update(MsgBean msgBean) {
		msgDao.update(msgBean);
	}
	
}
