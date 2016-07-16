package org.phoenix.node.dto;
/**
 * 任务类型
 * @author mengfeiyang
 *
 */
public enum TaskType {
	WEB_CASE("WEB_CASE"),
	WEB_SCENARIO("WEB_SCENARIO"),
	INTERFACE_CASE("INTERFACE_CASE"),
	INTERFACE_SCENARIO("INTERFACE_SCENARIO"),
	MOBILE_CASE("MOBILE_CASE"),
	MOBILE_SCENARIO("MOBILE_SCENARIO"),
	JMETER_HTTP_CASE("JMETER_HTTP_CASE"),
	JMETER_JDBC_CASE("JMETER_JDBC_CASE");
	
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
