package org.phoenix.web.dao.impl;

import java.util.List;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.enums.TaskStatusType;
import org.phoenix.web.dao.ITaskDao;
import org.phoenix.web.model.TaskModel;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDao extends BaseDao<TaskModel> implements ITaskDao{

	@Override
	public Pager<TaskModel> getTaskModelPagerByUser(int uid) {
		return super.find("from TaskModel t where t.user.id="+uid);
	}

	@Override
	public Pager<TaskModel> getTaskModelPagerByStatus(TaskStatusType status) {
		return super.find("from TaskModel where taskStatusType="+status);
	}

	@Override
	public Pager<TaskModel> getTaskModelPagerBySelect(int uid, String status,String taskType,String jobStatus) {
		return super.find("from TaskModel where taskStatusType like '%"+status+"%' And taskType like '%"+taskType+"%' And jobStatus like '%"+jobStatus+"%' And user.id="+uid);
	}

	@Override
	public List<TaskModel> getTaskModelListByJob() {
		return super.list("from TaskModel where jobStatus in('WAITING','RUNNING','ERROR')");
	}

	@Override
	public List<TaskModel> getTaskModelListByUid(int uid) {
		return super.list("from TaskModel t where t.user.id="+uid);
	}

	@Override
	public void updateBatchLogId(int batchId) {
		super.updateByHql("update TaskModel set batchLogId=0 where batchLogId="+batchId);
	}

}
