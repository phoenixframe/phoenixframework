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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
/**
 * 性能测试日志批次表
 * @author mengfeiyang
 *
 */
@Entity
@Table(name="l_perfbatch")
@BatchSize(size=30)
public class PerfBatchLogModel {
	private int id;
	private String startTime;
	private String endTime;
	private String continued;
	private String summary;
	private PhoenixJmeterBean phoenixJmeterBean;
	private Set<PerfLogModel> perfLogModels;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getContinued() {
		return continued;
	}
	public void setContinued(String continued) {
		this.continued = continued;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="perfCaseId")
	@LazyCollection(LazyCollectionOption.FALSE)
	public PhoenixJmeterBean getPhoenixJmeterBean() {
		return phoenixJmeterBean;
	}
	public void setPhoenixJmeterBean(PhoenixJmeterBean phoenixJmeterBean) {
		this.phoenixJmeterBean = phoenixJmeterBean;
	}
	@OneToMany(mappedBy="perfBatchLogModel",fetch=FetchType.EAGER,targetEntity=PerfLogModel.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
	public Set<PerfLogModel> getPerfLogModels() {
		return perfLogModels;
	}
	public void setPerfLogModels(Set<PerfLogModel> perfLogModels) {
		this.perfLogModels = perfLogModels;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}
