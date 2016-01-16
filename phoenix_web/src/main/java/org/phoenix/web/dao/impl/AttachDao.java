package org.phoenix.web.dao.impl;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.web.dao.IAttachDao;
import org.phoenix.web.model.AttachModel;
import org.springframework.stereotype.Repository;
@Repository
public class AttachDao extends BaseDao<AttachModel> implements IAttachDao{

	@Override
	public Pager<AttachModel> getAttachPager(int id) {
		return super.find("from AttachModel where user.id="+id);
	}

	@Override
	public Pager<AttachModel> getAttachPagerByKeyWord(int uid, String keyWord) {
		return super.find("from AttachModel where user.id="+uid+" And attachName like '%"+keyWord+"%'");
	}

}
