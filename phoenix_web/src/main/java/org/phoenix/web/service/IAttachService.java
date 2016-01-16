package org.phoenix.web.service;

import org.phoenix.basic.paging.Pager;
import org.phoenix.web.model.AttachModel;

public interface IAttachService {
	Pager<AttachModel> getAttachPager(int uid);
	Pager<AttachModel> getAttachPagerByKeyWord(int uid,String keyWord);
	void deleteAttach(int id);
	AttachModel getAttachModel(int id);
	AttachModel add(AttachModel attachModel);
}
