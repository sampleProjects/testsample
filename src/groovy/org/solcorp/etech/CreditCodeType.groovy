package org.solcorp.etech

public enum CreditCodeType {
	
	A(1, "A"),
	B(2, "B"),
	C(3, "C"),
	D(4, "D")
	
	private final String value
	private final String id
	
	public CreditCodeType(Integer id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public int getKey() {
		return id
	}
	
	public String getValue() {
		return value
	}
	
	static CreditCodeList() {
		return [A, B, C, D]
	}
	
	public String toString() { 
		return this.getValue() 
	}
}
