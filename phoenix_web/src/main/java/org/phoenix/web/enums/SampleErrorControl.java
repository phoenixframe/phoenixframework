package org.phoenix.web.enums;

public enum SampleErrorControl {
	CONTINUE("continue"),
	STOPTHREAD("stopthread"),
	STOPTEST("stoptest"),
	STOPTESTNOW("stoptestnow");
	
	private String name;
	private SampleErrorControl(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
