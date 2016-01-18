package org.phoenix.model;

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
@Table(name="t_interface_data")
public class InterfaceDataBean {
	private int id;
	private InterfaceBatchDataBean interfaceBatchDataBean;
	private String dataName;
	private String dataContent;
	
	public InterfaceDataBean(InterfaceBatchDataBean interfaceBatchDataBean,
			String dataName, String dataContent) {
		super();
		this.interfaceBatchDataBean = interfaceBatchDataBean;
		this.dataName = dataName;
		this.dataContent = dataContent;
	}
	public InterfaceDataBean() {
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
	@JoinColumn(name="interfaceBatchDataId")
	@LazyCollection(LazyCollectionOption.FALSE)
	public InterfaceBatchDataBean getInterfaceBatchDataBean() {
		return interfaceBatchDataBean;
	}
	public void setInterfaceBatchDataBean(
			InterfaceBatchDataBean interfaceBatchDataBean) {
		this.interfaceBatchDataBean = interfaceBatchDataBean;
	}
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
}
