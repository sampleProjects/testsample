package org.solcorp.etech

public enum InOutTrackingMethodType {
	
	WITH_OVERTIME(1, "Real Time With Overtime"),
	NO_OVERTIME(2, "Real Time No Overtime"),
	FREE_FORM(3, "Free Form")
		
	private final String value
	private final String id
	
	public InOutTrackingMethodType(Integer id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public int getKey() {
		return id
	}
	
	public String getValue() {
		return value
	}
	
	static StatementTypeList() {
		return [WITH_OVERTIME, NO_OVERTIME, FREE_FORM]
	}
	
	public String toString() { 
		return this.getValue() 
	}
}
