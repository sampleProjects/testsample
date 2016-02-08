package org.solcorp.etech

public enum StatementType {
	
	A(1, "A"),
	B(2, "B"),
	C(3, "C"),
	D(4, "D")
	
	private final String value
	private final String id
	
	public StatementType(Integer id, String value) {
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
		return [A, B, C, D]
	}
	
	public String toString() { 
		return this.getValue() 
	}
}
