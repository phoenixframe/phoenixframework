package org.phoenix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.phoenix.enums.LocatorType;

@Entity
@Table(name="t_web_unit")
public class LocatorBean {
	private int id;
	private String locatorDataName;
	private String locatorData;
	private LocatorType locatorType;
	private CaseBean caseBean;
	
	public LocatorBean() {
	}

	public LocatorBean(String locatorDataName, String locatorData,
			LocatorType locatorType, CaseBean caseBean) {
		super();
		this.locatorDataName = locatorDataName;
		this.locatorData = locatorData;
		this.locatorType = locatorType;
		this.caseBean = caseBean;
	}


	public LocatorBean(String locatorData, LocatorType locatorType) {
		super();
		this.locatorData = locatorData;
		this.locatorType = locatorType;
	}	
	
	public LocatorBean(String locatorData) {
		super();
		this.locatorData = locatorData;
		this.locatorType = LocatorType.CSS;
	}
	@Id
    @GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	@Column(unique=true,nullable=false)
	public String getLocatorDataName() {
		return locatorDataName;
	}

	public void setLocatorDataName(String locatorDataName) {
		this.locatorDataName = locatorDataName;
	}

	@Column(columnDefinition="text")
	public String getLocatorData() {
		return locatorData;
	}
	public void setLocatorData(String locatorData) {
		this.locatorData = locatorData;
	}
	@Enumerated(EnumType.STRING)
	public LocatorType getLocatorType() {
		return locatorType;
	}
	public void setLocatorType(LocatorType locatorType) {
		this.locatorType = locatorType;
	}
}
