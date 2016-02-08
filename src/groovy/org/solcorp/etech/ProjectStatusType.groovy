package org.solcorp.etech

/**
 * This ENUM is used to store four possible values for the status type of any entity.
 * @author hjjogiya
 *
 */
public enum ProjectStatusType {

 
	OPEN(1, "Open"),
	
	CLOSE(2, "Closed")	 
	
	private final String value
	private final Integer id

	ProjectStatusType(Integer id, String value) {		
		this.id = id
		this.value = value
	}
	
	public int getKey() {		
		return id
	}

	public String getValue() {
		return value
	}
	
	static projectStatusTypeList() {
		return [OPEN, CLOSE]
	} 
	
	public String toString() {
		return this.getValue()
	}	
}