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
/**
 * 单步日志表
 * @author mengfeiyang
 *
 */
@Entity
@Table(name="l_web_unit")
public class UnitLogBean {
	
	private int id;
	private String content;
	private String stepName;
	private String stepType;
	private String status;
	private String screenShot;
	private CaseLogBean caseLogBean;
	
	public UnitLogBean() {
	}
	
	public UnitLogBean(String content, String stepName, String stepType,
			String status, String screenShot, CaseLogBean caseLogBean) {
		super();
		this.content = content;
		this.stepName = stepName;
		this.stepType = stepType;
		this.status = status;
		this.screenShot = screenShot;
		this.caseLogBean = caseLogBean;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(columnDefinition="mediumtext",length = 16777215)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public String getScreenShot() {
		return screenShot;
	}
	public void setScreenShot(String screenShot) {
		this.screenShot = screenShot;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="caseLogId")
	@LazyCollection(LazyCollectionOption.FALSE)
	public CaseLogBean getCaseLogBean() {
		return caseLogBean;
	}
	public void setCaseLogBean(CaseLogBean caseLogBean) {
		this.caseLogBean = caseLogBean;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
}
