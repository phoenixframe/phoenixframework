package org.phoenix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="t_web_data")
public class DataBean {
	private int id;
	private String dataName;
	private String dataContent;
	private CaseBean caseBean;
	public DataBean() {
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(unique=true,nullable=false)
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getDataContent() {
		return dataContent;
	}
	public void setDataContent(String dataContent) {
		this.dataContent = dataContent;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="caseId")
	@LazyCollection(LazyCollectionOption.FALSE)
	public CaseBean getCaseBean() {
		return caseBean;
	}
	public void setCaseBean(CaseBean caseBean) {
		this.caseBean = caseBean;
	}
	
}
