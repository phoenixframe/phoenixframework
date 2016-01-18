package org.phoenix.enums;

/**
 * 消息发送类型<br>
 * NOT_SEND：任何情况都不发送<br>
 * CHECKPOINT_FAIL：检查点失败时才发送<br>
 * STEP_FAIL：执行步骤有失败时才发送<br>
 * CHECKPOINT_STEP_FAIL：检查点和执行步骤任何一个有失败都发送<br>
 * CASE_COMPLETE：任务执行完成后就发送。即使执行成功，也会发送一封执行完成的邮件<br>
 * @author mengfeiyang
 *
 */
public enum MsgSendType {
	NOT_SEND("NOT_SEND"),
	CHECKPOINT_FAIL("CHECKPOINT_FAIL"),
	STEP_FAIL("STEP_FAIL"),
	CHECKPOINT_STEP_FAIL("CHECKPOINT_STEP_FAIL"),
	CASE_COMPLETE("CASE_COMPLETE");
	
	private String name;

	private MsgSendType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
