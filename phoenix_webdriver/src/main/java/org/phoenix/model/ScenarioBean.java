package org.phoenix.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="t_web_scenario")
@BatchSize(size=30)
public class ScenarioBean {
	private int id;
	private String scenarioName;
	private String remark;
	private Date createDate;
	private int userId;
	private Set<CaseBean> caseBeans;
	
	public ScenarioBean() {
		// TODO Auto-generated constructor stub
	}
	
	public ScenarioBean(String scenarioName, String remark, Date createDate, Integer userId) {
		super();
		this.scenarioName = scenarioName;
		this.remark = remark;
		this.createDate = createDate;
		this.userId = userId;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@OneToMany(mappedBy="scenarioBean",fetch=FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
	public Set<CaseBean> getCaseBeans() {
		return caseBeans;
	}

	public void setCaseBeans(Set<CaseBean> caseBeans) {
		this.caseBeans = caseBeans;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Column(unique=true,nullable=false)
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date",insertable=true)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
