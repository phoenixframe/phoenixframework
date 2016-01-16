package org.phoenix.web.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

public class StatisticsDTO {
	private String batchId;
	private String startTime;
	private Timestamp createDate;
	private String scenarioName;
	private String casename;
	private String type;
	private BigInteger total;
	private BigDecimal success;
	private BigDecimal fail;
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getCasename() {
		return casename;
	}
	public void setCasename(String casename) {
		this.casename = casename;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigInteger getTotal() {
		return total;
	}
	public void setTotal(BigInteger total) {
		this.total = total;
	}
	public BigDecimal getSuccess() {
		return success;
	}
	public void setSuccess(BigDecimal success) {
		this.success = success;
	}
	public BigDecimal getFail() {
		return fail;
	}
	public void setFail(BigDecimal fail) {
		this.fail = fail;
	}
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
}
