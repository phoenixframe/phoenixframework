package org.phoenix.node.dao;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.node.model.TaskModel;
/**
 * 任务表操作
 * @author mengfeiyang
 *
 */
public class TaskDao extends HibernateDaoImpl<TaskModel>{
	
	public TaskModel getTaskModel(int id){
		return load(id);
	}
	
	public void updateTaskModel(TaskModel taskModel){
		update(taskModel);
	}
}
