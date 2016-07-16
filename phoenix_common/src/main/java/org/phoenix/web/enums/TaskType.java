package org.phoenix.web.enums;

/**
 * 平台支持的任务类型：<br>
 * 	WEB_CASE("WEB_CASE")：webUI测试任务<br>
 * 	WEB_SCENARIO("WEB_SCENARIO")：测试场景任务<br>
 * 	INTERFACE_CASE("INTERFACE_CASE")：接口测试任务<br>
 * 	MOBILE_CASE("MOBILE_CASE")：移动mobile测试任务<br>
 * @author mengfeiyang
 *
 */
public enum TaskType {
	WEB_CASE("WEB_CASE"),
	WEB_SCENARIO("WEB_SCENARIO"),
	INTERFACE_CASE("INTERFACE_CASE"),
	MOBILE_CASE("MOBILE_CASE");
	
	private String name;
	private TaskType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
