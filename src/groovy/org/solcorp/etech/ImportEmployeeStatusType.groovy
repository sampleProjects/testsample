package org.solcorp.etech

public enum ImportEmployeeStatusType {

 	INSERT_SUCCESS(1, "Inserted Successfully"),
	
	UPDATE_SUCCESS(2, "Updated Successfully"),
	
	PAY_DEPT_NOT_FOUND(3, "PayDept Not Found"),
	
	SUCCESS(4, "Success"),
	 
	FAILURE(5, "Failure"),
	
	OTHER(6, "Other")
	
	private final String value
	private final Integer id

	ImportEmployeeStatusType(Integer id, String value) {		
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
	
	static importEmployeeStatusTypeList() {
		return [INSERT_SUCCESS, UPDATE_SUCCESS, PAY_DEPT_NOT_FOUND, SUCCESS, FAILURE, OTHER]
	}
}