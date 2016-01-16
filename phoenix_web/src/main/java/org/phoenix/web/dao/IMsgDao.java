package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.MsgBean;

public interface IMsgDao extends IBaseDao<MsgBean>{
	Pager<MsgBean> getMsgBeanPager(int uid);
	Pager<MsgBean> getMsgBeanPagerByStatus(String msgStatusType,int uid);
	List<MsgBean> getMsgBeanListForEmail();
}
