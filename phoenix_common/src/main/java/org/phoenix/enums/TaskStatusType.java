package org.phoenix.enums;

/**
 * 任务状态<br>
 * NOT_RUNNING：任务新建时，还没有被调度执行<br>
 * RUNNING：任务正在执行<br>
 * SUCCESS：任务执行成功<br>
 * FAIL：任务执行失败<br>
 * STOP：任务被停止<br>
 * WAITING：任务已被添加到执行队列中<br>
 * @author mengfeiyang
 *
 */
public enum TaskStatusType {
	 NOT_RUNNING("NOT_RUNNING"),
     RUNNING("RUNNING"),
     SUCCESS("SUCCESS"),
     FAIL("FAIL"),
     STOP("STOP"),
     WAITING("WAITING");
     
 	private String name;
 	private TaskStatusType(String name) {
 		this.name = name;
 	}

 	public String getName() {
 		return name;
 	}

 	public void setName(String name) {
 		this.name = name;
 	}
}
