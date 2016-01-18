package org.phoenix.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.phoenix.enums.TaskStatusType;

@Entity
@Table(name="l_web_scenario")
public class ScenarioLogBean {
	private int id;
	private int uid;
	private String scenarioName;
	private TaskStatusType taskStatusType;
	private BatchLogBean batchLogBean;
	private String message;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="batchLogId")
	public BatchLogBean getBatchLogBean() {
		return batchLogBean;
	}
	public void setBatchLogBean(BatchLogBean batchLogBean) {
		this.batchLogBean = batchLogBean;
	}
	@Enumerated(EnumType.STRING)
	public TaskStatusType getTaskStatusType() {
		return taskStatusType;
	}
	public void setTaskStatusType(TaskStatusType taskStatusType) {
		this.taskStatusType = taskStatusType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
