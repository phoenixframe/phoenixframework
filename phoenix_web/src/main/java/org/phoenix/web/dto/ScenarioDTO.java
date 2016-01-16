package org.phoenix.web.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

public class ScenarioDTO {
	private int id;
	private int userId;
	private String scenarioName;
	private String remark;
	private Date createDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@NotBlank(message="场景的名称不能为空")
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	@NotBlank(message="场景的功能说明不能为空")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
