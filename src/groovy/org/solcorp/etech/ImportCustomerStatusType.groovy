package org.solcorp.etech

public enum ImportCustomerStatusType {

 	INSERT_SUCCESS(1, "Inserted Successfully"),
	
	UPDATE_SUCCESS(2, "Updated Successfully"),
	
	UPDATE_NOT_REQUIRED_SUCCESS(3, "Update Not Requried"),
	
	SUCCESS(4, "Success"),
	 
	FAILURE(5, "Failure"),
	
	OTHER(6, "Other")
	
	private final String value
	private final Integer id

	ImportCustomerStatusType(Integer id, String value) {		
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
	
	static importCustomerStatusTypeList() {
		return [INSERT_SUCCESS, UPDATE_SUCCESS, UPDATE_NOT_REQUIRED_SUCCESS, SUCCESS, FAILURE, OTHER]
	}
}