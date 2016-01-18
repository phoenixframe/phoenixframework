package org.phoenix.enums;

/**
 * 信息状态<br>
 * SUCCESS:消息发送成功
 * FAIL：消息发送失败
 * WAITING：消息正在等待被发送
 * SENDING：消息正在发送中
 * @author mengfeiyang
 *
 */
public enum MsgStatusType {
	SUCCESS("SUCCESS"),
	FAIL("FAIL"),
	WAITING("WAITING"),
	SENDING("SENDING");
	
	private String name;

	private MsgStatusType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
