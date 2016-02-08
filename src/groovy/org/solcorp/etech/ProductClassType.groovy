package org.solcorp.etech

public enum  ProductClassType {

	PROJECT(1, "Project"),
	
	SERVICES(2, "Services"),

	EXPENSES(3, "Expenses"),
	
	MISCELLANEOUS(4, "Miscellaneous"),
	
	private final String value
	private final Integer id
	
	ProductClassType(Integer id, String value) {
		this.id = id
		this.value = value
	}
	
	public int getKey() {
		return id
	}

	public String getValue() {
		return value
	}
	
	static classTypelist() {
		return [PROJECT, SERVICES, EXPENSES, MISCELLANEOUS]
	}
	
	public String toString() {
		return this.getValue()
	}
	
}
