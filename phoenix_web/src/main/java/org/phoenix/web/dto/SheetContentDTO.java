package org.phoenix.web.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class SheetContentDTO {
	private String caseName;
	private String sheetName;
	private boolean isRewrite;
	private String caseId;
	private String[] paramNames;
	private List<String[]> parameters;
	
	@NotBlank(message="Sheet名称不能为空!")
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public boolean getIsRewrite() {
		return isRewrite;
	}
	public void setIsRewrite(boolean isRewrite) {
		this.isRewrite = isRewrite;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String[] getParamNames() {
		return paramNames;
	}
	public void setParamNames(String[] paramNames) {
		this.paramNames = paramNames;
	}
	public List<String[]> getParameters() {
		return parameters;
	}
	public void setParameters(List<String[]> parameters) {
		this.parameters = parameters;
	}
}
