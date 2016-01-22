package org.phoenix.web.dto;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;
import org.phoenix.utils.GetNow;

public class PerformanceDTO {
	private int slaveId;
	
	private String caseName = "Jmeter用例";
	
	private String numThreads = "1";
	private String rampTime = "0";
	private String sampleErrorControl = "continue";
	private String controllerLoops = "1";
	
	private String taskAssort = "false";
	private String startTime = GetNow.getCurrentTime();
	private String endTime = GetNow.getCurrentTime();
	private String duration = "0";
	private String delayTime = "0";
	private String delayedStart = "true";
	
	private String domainURL = "";
	private String connectTimeOut = "";
	private String responseTimeOut = "";
	private String contentEncoding = "UTF-8";
	private String requestMethod = "GET";

	private String checkPointValue = "";
	private String checkPointType = "2";
	
	private String clearCache = "false";
	
	private String emailAttemper = "false";
	private String successLimit  = "2";
	private String failureLimit = "2";
	private String failureSubject = "Jmeter测试结果至少有"+failureLimit+"项失败";
	private String successSubject = "Jmeter测试结果至少有"+successLimit+"项成功";
	private String fromAddress = "";
	private String smtpHost = "";
	private String sendTo  = "";
	private String emailServerLoginName = "";
	private String emailServerLoginPassword = "";
	private String authType = "SSL";
	
	private String enableRendzvous = "false"; 
	private String groupSize = "1";
	private String rendzvousTimeOut = "30000";
	
	private String enableThinkTime = "false";
	private String thinkTime = "0";
	
	private String checkType = "response_data";
	
	private String enableProxy = "false";
	private String proxyHost = "";
	private String proxyPort = "80";
	private String proxyUserName = "";
	private String proxyPassword = "";
	
	private String enableDataSet = "false";
	private String fileEncoding = "UTF-8";
	private String filePath = "";
	private String recycle = "true";
	
	private String requestHeaders = "";
	
	private String monitedSlaves = "";
	private String useBodyString="false";
	private String bodyString = "";
	
	public int getSlaveId() {
		return slaveId;
	}
	public void setSlaveId(int slaveId) {
		this.slaveId = slaveId;
	}
	@NotBlank(message="用例名称不能为空")
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	@NotBlank(message="请填写并发的线程数")
	@Digits(integer=5,fraction=0,message="只能填写5位以内的数字")
	public String getNumThreads() {
		return numThreads;
	}
	public void setNumThreads(String numThreads) {
		this.numThreads = numThreads;
	}
	public String getRampTime() {
		return rampTime;
	}
	public void setRampTime(String rampTime) {
		this.rampTime = rampTime;
	}
	public String getSampleErrorControl() {
		return sampleErrorControl;
	}
	public void setSampleErrorControl(String sampleErrorControl) {
		this.sampleErrorControl = sampleErrorControl;
	}
	@NotBlank(message="单线程的迭代次数不能为空")
	@Digits(integer=5,fraction=0,message="只能填写5位以内的数字")
	public String getControllerLoops() {
		return controllerLoops;
	}
	public void setControllerLoops(String controllerLoops) {
		this.controllerLoops = controllerLoops;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}
	public String getDelayedStart() {
		return delayedStart;
	}
	public void setDelayedStart(String delayedStart) {
		this.delayedStart = delayedStart;
	}
	@NotBlank(message="url地址不能为空")
	public String getDomainURL() {
		return domainURL;
	}
	public void setDomainURL(String domainURL) {
		this.domainURL = domainURL;
	}
	public String getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(String connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	public String getResponseTimeOut() {
		return responseTimeOut;
	}
	public void setResponseTimeOut(String responseTimeOut) {
		this.responseTimeOut = responseTimeOut;
	}
	public String getContentEncoding() {
		return contentEncoding;
	}
	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getCheckPointValue() {
		return checkPointValue;
	}
	public void setCheckPointValue(String checkPointValue) {
		this.checkPointValue = checkPointValue;
	}
	public String getCheckPointType() {
		return checkPointType;
	}
	public void setCheckPointType(String checkPointType) {
		this.checkPointType = checkPointType;
	}
	public String getClearCache() {
		return clearCache;
	}
	public void setClearCache(String clearCache) {
		this.clearCache = clearCache;
	}
	public String getEmailAttemper() {
		return emailAttemper;
	}
	public void setEmailAttemper(String emailAttemper) {
		this.emailAttemper = emailAttemper;
	}
	public String getSuccessLimit() {
		return successLimit;
	}
	public void setSuccessLimit(String successLimit) {
		this.successLimit = successLimit;
	}
	public String getFailureLimit() {
		return failureLimit;
	}
	public void setFailureLimit(String failureLimit) {
		this.failureLimit = failureLimit;
	}
	public String getFailureSubject() {
		return failureSubject;
	}
	public void setFailureSubject(String failureSubject) {
		this.failureSubject = failureSubject;
	}
	public String getSuccessSubject() {
		return successSubject;
	}
	public void setSuccessSubject(String successSubject) {
		this.successSubject = successSubject;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getEmailServerLoginName() {
		return emailServerLoginName;
	}
	public void setEmailServerLoginName(String emailServerLoginName) {
		this.emailServerLoginName = emailServerLoginName;
	}
	public String getEmailServerLoginPassword() {
		return emailServerLoginPassword;
	}
	public void setEmailServerLoginPassword(String emailServerLoginPassword) {
		this.emailServerLoginPassword = emailServerLoginPassword;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getTaskAssort() {
		return taskAssort;
	}
	public void setTaskAssort(String taskAssort) {
		this.taskAssort = taskAssort;
	}
	public String getEnableRendzvous() {
		return enableRendzvous;
	}
	public void setEnableRendzvous(String enableRendzvous) {
		this.enableRendzvous = enableRendzvous;
	}
	public String getGroupSize() {
		return groupSize;
	}
	public void setGroupSize(String groupSize) {
		this.groupSize = groupSize;
	}
	public String getRendzvousTimeOut() {
		return rendzvousTimeOut;
	}
	public void setRendzvousTimeOut(String rendzvousTimeOut) {
		this.rendzvousTimeOut = rendzvousTimeOut;
	}
	public String getEnableThinkTime() {
		return enableThinkTime;
	}
	public void setEnableThinkTime(String enableThinkTime) {
		this.enableThinkTime = enableThinkTime;
	}
	public String getThinkTime() {
		return thinkTime;
	}
	public void setThinkTime(String thinkTime) {
		this.thinkTime = thinkTime;
	}
	public String getMonitedSlaves() {
		return monitedSlaves;
	}
	public void setMonitedSlaves(String monitedSlaves) {
		this.monitedSlaves = monitedSlaves;
	}
	public String getEnableProxy() {
		return enableProxy;
	}
	public void setEnableProxy(String enableProxy) {
		this.enableProxy = enableProxy;
	}
	public String getProxyHost() {
		return proxyHost;
	}
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	public String getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	public String getProxyUserName() {
		return proxyUserName;
	}
	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}
	public String getProxyPassword() {
		return proxyPassword;
	}
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}
	public String getEnableDataSet() {
		return enableDataSet;
	}
	public void setEnableDataSet(String enableDataSet) {
		this.enableDataSet = enableDataSet;
	}
	public String getFileEncoding() {
		return fileEncoding;
	}
	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getRecycle() {
		return recycle;
	}
	public void setRecycle(String recycle) {
		this.recycle = recycle;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getRequestHeaders() {
		return requestHeaders;
	}
	public void setRequestHeaders(String requestHeaders) {
		this.requestHeaders = requestHeaders;
	}
	public String getUseBodyString() {
		return useBodyString;
	}
	public void setUseBodyString(String useBodyString) {
		this.useBodyString = useBodyString;
	}
	public String getBodyString() {
		return bodyString;
	}
	public void setBodyString(String bodyString) {
		this.bodyString = bodyString;
	}
}
