package org.phoenix.web.quartz;

import org.phoenix.web.email.SpringBeanFactory;
import org.phoenix.web.model.TaskModel;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时任务工厂，对任务进行增删改操作
 * @author mengfeiyang
 *
 */

public class JobFactory {
    private static String JOB_GROUP_NAME = "phoenix.case.job";  
    
	public static String addJob(Class<? extends QuartzJobBean> clazz,TaskModel taskModel){
		try{
		 StdScheduler scheduler = (StdScheduler)SpringBeanFactory.getInstance().getBeanById("schedulerFactoryBean");	
		 TriggerKey triggerKey = TriggerKey.triggerKey(taskModel.getId()+"", JOB_GROUP_NAME);
         CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
         JobDetail jobDetail = JobBuilder.newJob(clazz)
                 .withIdentity(taskModel.getId()+"", JOB_GROUP_NAME)
                 .build();
         jobDetail.getJobDataMap().put("taskModel", taskModel);
         CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskModel.getTaskParameter());
          
         trigger = TriggerBuilder.newTrigger()
                 .withIdentity(taskModel.getId()+"", JOB_GROUP_NAME)
                 .withSchedule(scheduleBuilder)
                 .build();
          
         scheduler.scheduleJob(jobDetail, trigger);
		}catch(SchedulerException e){
			return e.getCause().toString();
		}
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public static String updateJob(TaskModel taskModel){
/*		try{
			 StdScheduler scheduler = (StdScheduler)SpringBeanFactory.getInstance().getBeanById("schedulerFactoryBean");
			 CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskModel.getTaskParameter());
			 TriggerKey triggerKey = TriggerKey.triggerKey(taskModel.getId()+"", JOB_GROUP_NAME);
			 CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	         trigger.getTriggerBuilder()
	                 .withIdentity(triggerKey)
	                 .withSchedule(scheduleBuilder)
	                 .build();
	         scheduler.getJobDetail(JobKey.jobKey(taskModel.getId()+"", JOB_GROUP_NAME)).getJobDataMap().put("taskModel", taskModel);
	         scheduler.rescheduleJob(triggerKey, trigger);
	         return "success";
		}catch(SchedulerException e){
			e.printStackTrace();
			return e.getCause().toString();
		}*/
		try{
			StdScheduler scheduler = (StdScheduler)SpringBeanFactory.getInstance().getBeanById("schedulerFactoryBean");
	        JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(taskModel.getId()+"", JOB_GROUP_NAME));  
	        Class<? extends QuartzJobBean> objJobClass = null;
	        try{
	        	objJobClass = (Class<? extends QuartzJobBean>) jobDetail.getJobClass(); 
	        }catch(Exception e){
	        	objJobClass = TaskHandler.class;
	        }
	        deleteJob(taskModel);
	        addJob(objJobClass,taskModel);
	        return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "异常信息："+e.getMessage();
		}
	}
	
	public static String stopJob(TaskModel taskModel){
		StdScheduler scheduler = (StdScheduler)SpringBeanFactory.getInstance().getBeanById("schedulerFactoryBean");
        JobKey jobKey = JobKey.jobKey(taskModel.getId()+"", JOB_GROUP_NAME);
        try {
            scheduler.pauseJob(jobKey);
            return "success";
        } catch (SchedulerException e) {
            return e.getCause().toString();
        }
	}
	
	public static String resumeJob(TaskModel taskModel){
		StdScheduler scheduler = (StdScheduler)SpringBeanFactory.getInstance().getBeanById("schedulerFactoryBean");
        JobKey jobKey = JobKey.jobKey(taskModel.getId()+"", JOB_GROUP_NAME);
        try {
            scheduler.resumeJob(jobKey);
            return "success";
        } catch (SchedulerException e) {
            return e.getCause().toString();
        }
	}
	
	public static String deleteJob(TaskModel taskModel){
		StdScheduler scheduler = (StdScheduler)SpringBeanFactory.getInstance().getBeanById("schedulerFactoryBean");
        JobKey jobKey = JobKey.jobKey(taskModel.getId()+"", JOB_GROUP_NAME);
        try {
        	scheduler.pauseTrigger(TriggerKey.triggerKey(taskModel.getId()+"", JOB_GROUP_NAME));// 停止触发器  
        	scheduler.unscheduleJob(TriggerKey.triggerKey(taskModel.getId()+"", JOB_GROUP_NAME));// 移除触发器  
            scheduler.deleteJob(jobKey);
            return "success";
        } catch (Exception e) {
        	e.printStackTrace();
            return e.getCause().toString();
        }
	}
}
