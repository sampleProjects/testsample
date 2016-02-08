package org.solcorp.etech

/**
 * This ENUM is used to store four possible values for the status type of any entity.
 * @author hjjogiya
 *
 */
public enum SessionStatusType {

 
	ACTIVE(1, "Active"),
	
	INACTIVE(2, "Inactive"),

	EXPIRED(3, "Expired"),
	
	EXPIRED_ON_RESET(3, "Expired On Reset")

	private final String value
	private final Integer id

	SessionStatusType(Integer id, String value) {		
		this.id = id
		this.value = value
	}
	
	public int getKey() {		
		return id
	}

	public String getValue() {
		return value
	}
	
	static SessionStatusTypeList() {
		return [ACTIVE, INACTIVE, EXPIRED, EXPIRED_ON_RESET]
	}
	 
	
	public String toString() {
		return this.getValue()
	}	
}