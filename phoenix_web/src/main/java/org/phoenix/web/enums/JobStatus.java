package org.phoenix.web.enums;

public enum JobStatus {
	NOT_JOB("NOT_JOB"),
	RUNNING("RUNNING"),
	WAITING("WAITING"),
	STOP("STOP"),
	ERROR("ERROR");
	
	private String name;
	private JobStatus(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
