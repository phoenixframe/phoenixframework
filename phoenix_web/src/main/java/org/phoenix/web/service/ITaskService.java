package org.phoenix.web.service;

import java.util.List;

import org.phoenix.basic.paging.Pager;
import org.phoenix.enums.TaskStatusType;
import org.phoenix.web.model.TaskModel;

/**
 * 任务服务接口
 * @author mengfeiyang
 *
 */
public interface ITaskService {
	
	/*
	 * 任务添加
	 */
	TaskModel add(TaskModel taskModel);
	/*
	 * 任务删除
	 */
	void delete(int id);
	/*
	 * 任务更新
	 */
	void update(TaskModel taskModel);
	
	void updateBatchLogId(int id);
	
	/*
	 * 加载一个任务
	 */
	TaskModel getTaskModel(int id);
	/*
	 * 根据用户加载任务，并加载分页信息
	 */
	Pager<TaskModel> getTaskModelPagerByUser(int uid);
	
	/*
	 * 根据任务状态加载，并加载分页信息
	 */
	Pager<TaskModel> getTaskModelPagerByStatus(TaskStatusType taskStatusType);
	
	Pager<TaskModel> getTaskModelPagerBySelect(int uid, String status,String taskType,String jobStatus);
	
	List<TaskModel> getTaskModelListForJob();
	List<TaskModel> getTaskModelListByUid(int uid);

}
