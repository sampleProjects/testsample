package org.solcorp.etech

public enum ImportExpenseStatusType {

 	EXPENSE_NOT_FOUND(1, "Expense Not Found"),
	
	PROJECT_NOT_FOUND(2, "Project Not Found"),
	 
	SUCCESS(3, "Success"),
	
	FAILURE(4, "Failure"),
	
	OTHER(5, "Other")
	
	private final String value
	private final Integer id

	ImportExpenseStatusType(Integer id, String value) {		
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
	
	static importExpenseStatusTypeList() {
		return [EXPENSE_NOT_FOUND, PROJECT_NOT_FOUND, SUCCESS, FAILURE, OTHER]
	}
}