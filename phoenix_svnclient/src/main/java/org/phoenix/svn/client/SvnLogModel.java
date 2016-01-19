package org.phoenix.svn.client;

import java.util.Date;
import java.util.List;

public class SvnLogModel {
 
    public long getRevision() {
        return revision;
    }
    public void setRevision(long revision) {
        this.revision = revision;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getLogEntry() {
        return logEntry;
    }
    public void setLogEntry(String logEntry) {
        this.logEntry = logEntry;
    }
    public int getEntryCount() {
        return entryCount;
    }
    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }
    public List<String> getChangedPaths() {
		return changedPaths;
	}
	public void setChangedPaths(List<String> changedPaths) {
		this.changedPaths = changedPaths;
	}
	private long revision;
    private String author;
    private Date date;
    private String message;
    private String logEntry;
    private int entryCount;
    private List<String> changedPaths;
}