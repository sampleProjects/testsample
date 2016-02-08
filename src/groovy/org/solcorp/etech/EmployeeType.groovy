package org.solcorp.etech

/**
 * This ENUM is used to store five possible values for the Employee type of any entity.
 * @author hjjogiya
 *
 */
public enum EmployeeType {
 
	SALARY(1, "Salary"),
	
	CONTRACT(2, "Contract"),

	NON_EXEMPT(3, "Non Exempt"),

	HOURLY(4, "Hourly"),
	
	ALL(5, "All")
	
	private final String value
	private final Integer id

	EmployeeType(Integer id, String value) {
		this.id = id
		this.value = value
	}
	
	public int getKey() {
		return id
	}

	public String getValue() {
		return value
	}
	
	static employeeTypelist() {
		return [SALARY, CONTRACT, NON_EXEMPT, HOURLY, ALL]
	}
	
	public String toString() {
		return this.getValue()
	}
}
