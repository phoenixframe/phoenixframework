package org.phoenix.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="l_web_case")
public class CaseLogBean {
	
	private int id;
	private String actor;
	private String clientIP;
	private String EngineType;
	private String StartTime;
	private String EndTime;
	private String duration;
	private String status;
	private String caseName;
	private String attachPath;
	private int scenarioLogBeanId;
	private BatchLogBean batchLogBean;
	private Set<UnitLogBean> unitLogBeans;
	
	public CaseLogBean() {
	}
	
	public CaseLogBean(String actor, String clientIP, String engineType,
			String startTime, String endTime, String duration, String status,
			int scenarioLogBeanId) {
		super();
		this.actor = actor;
		this.clientIP = clientIP;
		EngineType = engineType;
		StartTime = startTime;
		EndTime = endTime;
		this.duration = duration;
		this.status = status;
		this.scenarioLogBeanId = scenarioLogBeanId;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public String getEngineType() {
		return EngineType;
	}
	public void setEngineType(String engineType) {
		EngineType = engineType;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public int getScenarioLogBeanId() {
		return scenarioLogBeanId;
	}

	public void setScenarioLogBeanId(int scenarioLogBeanId) {
		this.scenarioLogBeanId = scenarioLogBeanId;
	}
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="batchLogId")
	public BatchLogBean getBatchLogBean() {
		return batchLogBean;
	}

	public void setBatchLogBean(BatchLogBean batchLogBean) {
		this.batchLogBean = batchLogBean;
	}

	@OneToMany(mappedBy="caseLogBean",targetEntity=UnitLogBean.class)
    @Fetch(FetchMode.SUBSELECT)
	public Set<UnitLogBean> getUnitLogBeans() {
		return unitLogBeans;
	}

	public void setUnitLogBeans(Set<UnitLogBean> unitLogBeans) {
		this.unitLogBeans = unitLogBeans;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
}
