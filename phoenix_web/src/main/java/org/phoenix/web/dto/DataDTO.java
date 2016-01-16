package org.phoenix.web.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.phoenix.model.DataBean;

public class DataDTO {
	private int id;
	private String dataName;
	private String dataContent;
	private List<DataBean> caseDataBeanList;
	private int caseId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@NotBlank(message="数据的名称标识不能为空")
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
	@NotNull(message="请选择一个用例")
	public int getCaseId() {
		return caseId;
	}
	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	public List<DataBean> getCaseDataBeanList() {
		return caseDataBeanList;
	}
	public void setCaseDataBeanList(List<DataBean> caseDataBeanList) {
		this.caseDataBeanList = caseDataBeanList;
	}
}
