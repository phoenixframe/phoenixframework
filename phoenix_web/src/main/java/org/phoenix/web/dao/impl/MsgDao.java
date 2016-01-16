package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.model.MsgBean;
import org.phoenix.web.dao.IMsgDao;
import org.springframework.stereotype.Repository;

@Repository
public class MsgDao extends BaseDao<MsgBean> implements IMsgDao{
	@Override
	public Pager<MsgBean> getMsgBeanPager(int uid) {
		return super.find("from MsgBean where userId="+uid+" order by id desc");
	}

	@Override
	public Pager<MsgBean> getMsgBeanPagerByStatus(String msgStatusType,int uid) {
		return find("from MsgBean where userId="+uid+" And msgStatusType like '%"+msgStatusType+"%' order by id desc");
	}

	@Override
	public List<MsgBean> getMsgBeanListForEmail() {
		String sql = "SELECT * from t_msgpool t WHERE t.msgStatusType IN ('WAITING' , 'FAIL') LIMIT 0,15;";
		return super.listBySql(sql, MsgBean.class, true);
	}

}
