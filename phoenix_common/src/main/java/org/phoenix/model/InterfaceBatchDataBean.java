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

@Entity
@Table(name="t_interface_batchdata")
@BatchSize(size=30)
public class InterfaceBatchDataBean {
	private int id;
	private CaseBean caseBean;
	private String expectData;
	private String remark;
	private Set<InterfaceDataBean> interfaceDatas;
	
	public InterfaceBatchDataBean(CaseBean caseBean, String expectData,String remark) {
		super();
		this.caseBean = caseBean;
		this.expectData = expectData;
		this.remark = remark;
	}

	public InterfaceBatchDataBean() {
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
	public String getExpectData() {
		return expectData;
	}

	public void setExpectData(String expectData) {
		this.expectData = expectData;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(mappedBy="interfaceBatchDataBean",targetEntity=InterfaceDataBean.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Fetch(FetchMode.SUBSELECT)
	public Set<InterfaceDataBean> getInterfaceDatas() {
		return interfaceDatas;
	}

	public void setInterfaceDatas(Set<InterfaceDataBean> interfaceDatas) {
		this.interfaceDatas = interfaceDatas;
	}
	
}
