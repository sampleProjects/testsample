package org.solcorp.etech

public enum ImportProjectStatusType {

 	INSERT_SUCCESS(1, "Inserted Successfully"),
	
	UPDATE_SUCCESS(2, "Updated Successfully"),
	
	UPDATE_NOT_REQUIRED_SUCCESS(3, "Update Not Requried"),
	
	CUSTOMER_NOT_FOUND(4, "Customer Not Found"),
	
	SUCCESS(5, "Success"),
	 
	FAILURE(6, "Failure"),
	
	OTHER(7, "Other")
	
	private final String value
	private final Integer id

	ImportProjectStatusType(Integer id, String value) {		
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
	
	static importProjectStatusTypeList() {
		return [INSERT_SUCCESS, UPDATE_SUCCESS, UPDATE_NOT_REQUIRED_SUCCESS, CUSTOMER_NOT_FOUND, SUCCESS, FAILURE, OTHER]
	}
}