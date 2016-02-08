package org.solcorp.etech

public enum AddressType {
	
	BILLING(1, "Billing"),
	SHIPPING(2, "Shipping"),
		
	private final String value
	private final String id
	
	public AddressType(Integer id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public int getKey() {
		return id
	}
	
	public String getValue() {
		return value
	}
	
	static StatementTypeList() {
		return [BILLING, SHIPPING]
	}
	
	public String toString() { 
		return this.getValue() 
	}
}
