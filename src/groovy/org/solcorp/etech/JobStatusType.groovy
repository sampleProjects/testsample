package org.solcorp.etech

public enum JobStatusType {

	RUNNING(1, "Running"),
	
	SUCCESS(2, "Success"),

	FAIL(3, "Fail")	
	
	private final String value
	private final Integer id

	JobStatusType(Integer id, String value) {		
		this.id = id
		this.value = value
	}
	
	public int getKey() {		
		return id
	}

	public String getValue() {
		return value
	}
	
	public String toString() {
		return this.getValue()
	}	
}