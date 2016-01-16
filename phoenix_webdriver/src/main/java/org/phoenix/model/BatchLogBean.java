package org.phoenix.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="l_web_batch")
@BatchSize(size=30)
public class BatchLogBean {
	private int id;
	private String batchId;
	private String taskType;
	private Date createDate;
	private int uid;
	private Set<ScenarioLogBean> scenarioLogBeans;
	private Set<CaseLogBean> caseLogBeans;
	public BatchLogBean() {
	}
	public BatchLogBean(String batchId) {
		super();
		this.batchId = batchId;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	@OneToMany(mappedBy="batchLogBean",targetEntity=ScenarioLogBean.class)
	@Fetch(FetchMode.SUBSELECT)
	public Set<ScenarioLogBean> getScenarioLogBeans() {
		return scenarioLogBeans;
	}
	public void setScenarioLogBeans(Set<ScenarioLogBean> scenarioLogBeans) {
		this.scenarioLogBeans = scenarioLogBeans;
	}
	@OneToMany(mappedBy="batchLogBean",targetEntity=CaseLogBean.class)
	@Fetch(FetchMode.SUBSELECT)
	public Set<CaseLogBean> getCaseLogBeans() {
		return caseLogBeans;
	}
	public void setCaseLogBeans(Set<CaseLogBean> caseLogBeans) {
		this.caseLogBeans = caseLogBeans;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
}
