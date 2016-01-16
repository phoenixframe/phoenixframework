package org.phoenix.web.quartz;

import java.util.List;

import org.phoenix.web.email.SpringBeanFactory;
import org.phoenix.web.enums.JobStatus;
import org.phoenix.web.model.TaskModel;
import org.phoenix.web.service.ITaskService;

/**
 * 初始化任务，系统重新启动时会将任务表中Job状态为ERROR，RUNNING类型的任务重新<br>
 * 添加到定时器队列重新执行
 * @author mengfeiyang
 *
 */
public class InitTask {

	public void init(){
		new Thread(){
			@Override
			public void run(){
				ITaskService taskService = (ITaskService)SpringBeanFactory.getInstance().getBeanById("taskService");
				List<TaskModel> taskList = taskService.getTaskModelListForJob();
				for(TaskModel t : taskList){
					String r = JobFactory.addJob(TaskHandler.class, t);
					if(!r.equals("success")){
						t.setJobStatus(JobStatus.ERROR);
						t.setMessage("init error,"+r);
					}
				}
			}
		}.start();
	}
}
