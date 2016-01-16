package org.phoenix.web.quartz;

import java.util.Date;

import org.phoenix.enums.TaskStatusType;
import org.phoenix.web.dto.AjaxObj;
import org.phoenix.web.email.SpringBeanFactory;
import org.phoenix.web.enums.JobStatus;
import org.phoenix.web.model.SlaveModel;
import org.phoenix.web.model.TaskModel;
import org.phoenix.web.service.ITaskService;
import org.phoenix.web.util.HttpRequestSender;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSON;
/**
 * 执行定时任务，此类接收的参数是TaskModel，可处理多种任务类型
 * @author mengfeiyang
 *
 */
public class TaskHandler extends QuartzJobBean{
	@Override
	protected void executeInternal(JobExecutionContext context)throws JobExecutionException {
		TaskModel taskModel = (TaskModel)context.getJobDetail().getJobDataMap().get("taskModel");
		SlaveModel slaveModel = taskModel.getSlaveModel();
		ITaskService taskService = (ITaskService)SpringBeanFactory.getInstance().getBeanById("taskService");
		String url = "http://"+slaveModel.getSlaveIP()+"/phoenix_node/action.do?taskId="+taskModel.getId()+"&taskType="+taskModel.getTaskType();
		try {
			String ajaxObj = HttpRequestSender.getResponseByPost(url);
			AjaxObj o = JSON.parseObject(ajaxObj, AjaxObj.class);
			
			if(o.getResult() != 0)taskModel.setTaskStatusType(TaskStatusType.RUNNING);
			else taskModel.setTaskStatusType(TaskStatusType.FAIL);
			taskModel.setJobStatus(JobStatus.RUNNING);
			taskModel.setMessage("定时任务正常");
		} catch (Exception e) {
			e.printStackTrace();
			taskModel.setJobStatus(JobStatus.ERROR);
			taskModel.setMessage("定时任务异常，"+e.getMessage());
		}
		taskModel.setLastTime(new Date());
		taskService.update(taskModel);
	}
}
