package org.solcorp.etech

/**
 * This ENUM is used to store four possible values for the status type of any entity.
 * @author hjjogiya
 *
 */
public enum StatusType {

 
	ACTIVE(1, "Active"),
	
	INACTIVE(2, "Inactive"),

	TERMINATED(3, "Terminated")

	private final String value
	private final Integer id

	StatusType(Integer id, String value) {		
		this.id = id
		this.value = value
	}
	
	public int getKey() {		
		return id
	}

	public String getValue() {
		return value
	}
	
	static employeeStatusTypeList() {
		return [ACTIVE, INACTIVE, TERMINATED]
	}
	
	static userStatusTypeList() {
		return [ACTIVE, INACTIVE]
	}
	
	static customerStatusTypeList(){
		return [ACTIVE, INACTIVE]
	}
	
	public String toString() {
		return this.getValue()
	}	
}