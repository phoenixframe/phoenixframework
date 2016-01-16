package org.phoenix.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.phoenix.enums.MsgSendType;
import org.phoenix.model.ScenarioBean;

public class CaseDTO {
	private int id;
	private Integer scenId;
	private String caseName;
	private String codeContent;
	private String className;
	private String caseType;
	private MsgSendType msgSendType;
	private boolean isDeleteMsg;
	private String remark;
	private int status = 1;
	private Date createDate;
	private ScenarioBean scenarioBean;
	private int userId;
	
	public CaseDTO() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@NotNull(message="输入场景Id")
	public Integer getScenId() {
		return scenId;
	}
	public void setScenId(Integer scenId) {
		this.scenId = scenId;
	}
	@NotEmpty(message="用例名称不能为空")
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCodeContent() {
		return codeContent;
	}
	public void setCodeContent(String codeContent) {
		this.codeContent = codeContent;
	}
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@NotEmpty(message="请填写用例功能说明")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public ScenarioBean getScenarioBean() {
		return scenarioBean;
	}
	public void setScenarioBean(ScenarioBean scenarioBean) {
		this.scenarioBean = scenarioBean;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public MsgSendType getMsgSendType() {
		return msgSendType;
	}

	public void setMsgSendType(MsgSendType msgSendType) {
		this.msgSendType = msgSendType;
	}

	public boolean isDeleteMsg() {
		return isDeleteMsg;
	}

	public void setDeleteMsg(boolean isDeleteMsg) {
		this.isDeleteMsg = isDeleteMsg;
	}
}
