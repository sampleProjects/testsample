package org.solcorp.etech

public enum PaymentTermsType {
	
	NET_30(1, "Net 30"),
	NET_15(2, "Net 15"),
	NET_10(3, "Net 10"),
	ON_RECEIPT(4, "On Receipt")
	
	private final String value
	private final String id
	
	public PaymentTermsType(Integer id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public int getKey() {
		return id
	}
	
	public String getValue() {
		return value
	}
	
	static PaymentTermsList() {
		return [NET_30, NET_15, NET_10, ON_RECEIPT]
	}
	
	public String toString() { 
		return this.getValue() 
	}
}
