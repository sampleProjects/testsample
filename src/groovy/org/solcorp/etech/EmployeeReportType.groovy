package org.solcorp.etech

/**
 * This ENUM is used to store two possible values for the Employee Report Type of any entity.
 * @author hjjogiya
 *
 */
public enum EmployeeReportType {

 
	ALL_PROJECTS(1, "All Projects"),
	
	ASSIGNED_PROJECTS(2, "Assigned Projects")
	
	private final String value
	private final Integer id

	EmployeeReportType(Integer id, String value) {		
		this.id = id
		this.value = value
	}
	
	public int getKey() {		
		return id
	}

	public String getValue() {
		return value
	}
	
	static employeeReportTypeList() {
		return [ALL_PROJECTS, ASSIGNED_PROJECTS]
	}
	
	public String toString() {
		return this.getValue()
	}	
}