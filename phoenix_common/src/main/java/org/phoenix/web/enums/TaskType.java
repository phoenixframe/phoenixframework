package org.phoenix.web.enums;

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
