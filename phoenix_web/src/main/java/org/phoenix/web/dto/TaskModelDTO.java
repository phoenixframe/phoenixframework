package org.phoenix.web.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.phoenix.enums.TaskStatusType;
import org.phoenix.web.enums.JobStatus;
import org.phoenix.web.enums.TaskType;
import org.phoenix.web.model.User;

public class TaskModelDTO {
		
		private int id;
		private TaskType taskType;
		private String taskName;
		private String taskData;
		private String beanName;
		private int slaveId;
		private String taskParameter;
		private String message;
		private JobStatus jobStatus;
		private TaskStatusType taskStatusType;
		private Date startTime;
		private Date endTime;
		private User user;
		public TaskModelDTO() {
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		@Enumerated(EnumType.STRING)
		public TaskType getTaskType() {
			return taskType;
		}
		public void setTaskType(TaskType taskType) {
			this.taskType = taskType;
		}
		@NotBlank(message="任务名称不能为空")
		public String getTaskName() {
			return taskName;
		}
		public void setTaskName(String taskName) {
			this.taskName = taskName;
		}
		@NotBlank(message="需要执行的场景或用例不能为空")
		public String getTaskData() {
			return taskData;
		}
		
		public JobStatus getJobStatus() {
			return jobStatus;
		}

		public void setJobStatus(JobStatus jobStatus) {
			this.jobStatus = jobStatus;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public void setTaskData(String taskData) {
			this.taskData = taskData;
		}

		public int getSlaveId() {
			return slaveId;
		}

		public void setSlaveId(int slaveId) {
			this.slaveId = slaveId;
		}

		public String getTaskParameter() {
			return taskParameter;
		}
		public void setTaskParameter(String taskParameter) {
			this.taskParameter = taskParameter;
		}
		@Enumerated(EnumType.STRING)
		public TaskStatusType getTaskStatusType() {
			return taskStatusType;
		}
		public void setTaskStatusType(TaskStatusType taskStatusType) {
			this.taskStatusType = taskStatusType;
		}
		@Temporal(TemporalType.TIMESTAMP)
		public Date getStartTime() {
			return startTime;
		}
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		@Temporal(TemporalType.TIMESTAMP)
		public Date getEndTime() {
			return endTime;
		}
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}

		public String getBeanName() {
			return beanName;
		}

		public void setBeanName(String beanName) {
			this.beanName = beanName;
		}
}
