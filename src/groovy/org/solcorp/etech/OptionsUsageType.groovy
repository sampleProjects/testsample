package org.solcorp.etech

public enum OptionsUsageType {
	
	NONE(1, "None"),
	BY_PAY_DEPT(2, "By Pay Dept"),
	BY_EMPLOYEE(3, "By Employee"),
	ALL(4, "All")
		
	private final String value
	private final String id
	
	public OptionsUsageType(Integer id, String value) {
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
		return [NONE, BY_PAY_DEPT, BY_EMPLOYEE, ALL]
	}
	
	public String toString() { 
		return this.getValue() 
	}
}
