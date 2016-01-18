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
@Table(name="l_performance")
public class PerfLogModel {
	private int id;
	private int setNumThreads;
	private String avgPageBytes;
	private String bytesPerSecond;
	private String errorPercentage;
	private String kbPerSecond;
	private String maxResponseTime;
	private String meanResponseTime;
	private String minResponseTime;
	private String totalBytes;
	private String totalCount;
	private String summary;
	private String monitedSlaveMetrics;
	private PerfBatchLogModel perfBatchLogModel;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSetNumThreads() {
		return setNumThreads;
	}
	public void setSetNumThreads(int setNumThreads) {
		this.setNumThreads = setNumThreads;
	}
	public String getAvgPageBytes() {
		return avgPageBytes;
	}
	public void setAvgPageBytes(String avgPageBytes) {
		this.avgPageBytes = avgPageBytes;
	}
	public String getBytesPerSecond() {
		return bytesPerSecond;
	}
	public void setBytesPerSecond(String bytesPerSecond) {
		this.bytesPerSecond = bytesPerSecond;
	}
	public String getErrorPercentage() {
		return errorPercentage;
	}
	public void setErrorPercentage(String errorPercentage) {
		this.errorPercentage = errorPercentage;
	}
	public String getKbPerSecond() {
		return kbPerSecond;
	}
	public void setKbPerSecond(String kbPerSecond) {
		this.kbPerSecond = kbPerSecond;
	}
	public String getMaxResponseTime() {
		return maxResponseTime;
	}
	public void setMaxResponseTime(String maxResponseTime) {
		this.maxResponseTime = maxResponseTime;
	}
	public String getMeanResponseTime() {
		return meanResponseTime;
	}
	public void setMeanResponseTime(String meanResponseTime) {
		this.meanResponseTime = meanResponseTime;
	}
	public String getMinResponseTime() {
		return minResponseTime;
	}
	public void setMinResponseTime(String minResponseTime) {
		this.minResponseTime = minResponseTime;
	}
	public String getTotalBytes() {
		return totalBytes;
	}
	public void setTotalBytes(String totalBytes) {
		this.totalBytes = totalBytes;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="perfBatchId")
	@LazyCollection(LazyCollectionOption.FALSE)
	public PerfBatchLogModel getPerfBatchLogModel() {
		return perfBatchLogModel;
	}
	public void setPerfBatchLogModel(PerfBatchLogModel perfBatchLogModel) {
		this.perfBatchLogModel = perfBatchLogModel;
	}
	public String getMonitedSlaveMetrics() {
		return monitedSlaveMetrics;
	}
	public void setMonitedSlaveMetrics(String monitedSlaveMetrics) {
		this.monitedSlaveMetrics = monitedSlaveMetrics;
	}
	
}
