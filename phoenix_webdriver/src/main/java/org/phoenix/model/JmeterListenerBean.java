package org.phoenix.model;

/**
 * 监控执行结果
 * @author mengfeiyang
 *
 */
public class JmeterListenerBean {
	private int id;
	private int perfCaseId;
	private String startTime;
	private String endTime;
	private int setNumThreads;
	private int totalThreads;
	private int activeThreads;
	private int finishedThreads;
	private int startedThreads;
	private String queueString;
	private String localIP;
	private String summary;
	private String resultCal;
	private String monitedSlaves;
	private boolean isRunning;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPerfCaseId() {
		return perfCaseId;
	}
	public void setPerfCaseId(int perfCaseId) {
		this.perfCaseId = perfCaseId;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
	public int getSetNumThreads() {
		return setNumThreads;
	}
	public void setSetNumThreads(int setNumThreads) {
		this.setNumThreads = setNumThreads;
	}
	public int getTotalThreads() {
		return totalThreads;
	}
	public void setTotalThreads(int totalThreads) {
		this.totalThreads = totalThreads;
	}
	public int getActiveThreads() {
		return activeThreads;
	}
	public void setActiveThreads(int activeThreads) {
		this.activeThreads = activeThreads;
	}
	public int getFinishedThreads() {
		return finishedThreads;
	}
	public void setFinishedThreads(int finishedThreads) {
		this.finishedThreads = finishedThreads;
	}
	public int getStartedThreads() {
		return startedThreads;
	}
	public void setStartedThreads(int startedThreads) {
		this.startedThreads = startedThreads;
	}
	public String getQueueString() {
		return queueString;
	}
	public void setQueueString(String queueString) {
		this.queueString = queueString;
	}
	public String getLocalIP() {
		return localIP;
	}
	public void setLocalIP(String localIP) {
		this.localIP = localIP;
	}
	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	public String getResultCal() {
		return resultCal;
	}
	public void setResultCal(String resultCal) {
		this.resultCal = resultCal;
	}
	public String getMonitedSlaves() {
		return monitedSlaves;
	}
	public void setMonitedSlaves(String monitedSlaves) {
		this.monitedSlaves = monitedSlaves;
	}
	
}
