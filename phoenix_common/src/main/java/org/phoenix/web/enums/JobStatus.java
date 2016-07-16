package org.phoenix.web.enums;
/**
 * 任务状态类型：<br>
 * 	NOT_JOB("NOT_JOB"):非定时任务<br>
 * 	RUNNING("RUNNING"):正在运行<br>
 * 	WAITING("WAITING"):等待运行<br>
 * 	STOP("STOP"):停止状态<br>
 * 	ERROR("ERROR"):执行出错<br>
 * @author mengfeiyang
 *
 */
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
