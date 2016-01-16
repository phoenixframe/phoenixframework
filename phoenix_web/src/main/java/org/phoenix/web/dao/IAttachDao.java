package org.phoenix.web.dao;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.web.model.AttachModel;

public interface IAttachDao extends IBaseDao<AttachModel>{
	Pager<AttachModel> getAttachPager(int id);
	Pager<AttachModel> getAttachPagerByKeyWord(int uid,String keyWord);
}
