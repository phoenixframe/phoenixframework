package org.phoenix.web.service;

import java.util.List;

import org.phoenix.basic.paging.Pager;
import org.phoenix.model.MsgBean;

public interface IMsgService {
	void deleteMsg(int id);
	MsgBean getMsgBean(int id);
	Pager<MsgBean> getMsgBeanPager(int uid);
	Pager<MsgBean> getMsgBeanPagerByStatus(String msgStatusType,int uid);
	List<MsgBean> getMsgBeanListForEmail();
	void update(MsgBean msgBean);

}
