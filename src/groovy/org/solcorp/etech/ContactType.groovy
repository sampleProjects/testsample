package org.solcorp.etech

public enum ContactType {
	
	BILLING(1, "Billing"),
	ADMIN(2, "Admin"),
	SALES(3, "Sales"),
	OTHER(4, "Other")
	
	private final String value
	private final String id
	
	public ContactType(Integer id, String value) {
		this.id = id
		this.value = value
	}
	
	public int getKey() {
		return id
	}
	
	public String getValue() {
		return value
	}
	
	static contactTypeList() {
		return [BILLING, ADMIN, SALES, OTHER]
	}
	
	public String toString() {
		return this.getValue()
	}
}
