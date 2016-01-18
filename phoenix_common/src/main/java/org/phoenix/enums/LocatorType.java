package org.phoenix.enums;

public enum LocatorType {
	CSS("CSS"),
	ID("ID"),
	CLASS("CLASS"),
	NAME("NAME"),
	TAGNAME("TAGNAME"),
	LINKTEXT("LINKTEXT"),
	XPATH("XPATH"),
	PARTIALLINKTEXT("PARTIALLINKTEXT");
	
	private String name;
	private LocatorType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
