package org.phoenix.web.dao;

import java.util.List;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.enums.TaskStatusType;
import org.phoenix.web.model.TaskModel;

/**
 * 任务数据层
 * @author mengfeiyang
 *
 */
public interface ITaskDao extends IBaseDao<TaskModel>{
	/*
	 * 获取当前用户下的任务
	 */
     Pager<TaskModel> getTaskModelPagerByUser(int uid);
     List<TaskModel> getTaskModelListByUid(int uid);
     
     /*
      * 根据任务类型筛选
      */
     Pager<TaskModel> getTaskModelPagerByStatus(TaskStatusType status);
     Pager<TaskModel> getTaskModelPagerBySelect(int uid,String status,String taskType,String jobStatus);
     List<TaskModel> getTaskModelListByJob();
     void updateBatchLogId(int batchId);
}
