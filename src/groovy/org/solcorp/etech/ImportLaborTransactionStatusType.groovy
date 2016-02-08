package org.solcorp.etech

public enum ImportLaborTransactionStatusType {

 	PROJECT_NOT_FOUND(1, "Project Not Found"),
	
	EMPLOYEE_NOT_FOUND(2, "Employee Not Found"),
	
	PAY_DEPT_NOT_FOUND(3, "Paydept Not Found"),
	
	LABOR_ACT_DEPT_NOT_FOUND(4, "Labor Department Not Found"),
	
	LABOR_ACTIVITY_NOT_FOUND(5, "Labor Activity Not Found"),
	
	UNDEFINED_LABOR_ACTIVITY(6, "Undefined Labor Activity"),
	
	INVALID_STD_RATE(7, "Invalid Standard Rate"),
	
	SUCCESS(8, "Success"),
	
	FAILURE(9, "Failure"),
	
	OTHER(10, "Other")
	
	private final String value
	private final Integer id

	ImportLaborTransactionStatusType(Integer id, String value) {		
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
	
	static importLaborTransactionStatusTypeList() {
		return [PROJECT_NOT_FOUND, EMPLOYEE_NOT_FOUND, PAY_DEPT_NOT_FOUND, LABOR_ACT_DEPT_NOT_FOUND, LABOR_ACTIVITY_NOT_FOUND, UNDEFINED_LABOR_ACTIVITY, INVALID_STD_RATE, SUCCESS, FAILURE, OTHER]
	}
}