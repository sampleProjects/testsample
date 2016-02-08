package org.solcorp.etech

public enum BillCodeType {
	
	TIME_AND_MATERIAL(1, "Time AND Material"),
	FLAT_RATE(2, "Flat Rate"),
	PROSPECTIVE(3, "Prospective"),
	INTERNAL(4, "Internal")
	
	private final String value
	private final String id
	
	public BillCodeType(Integer id, String value) {
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
		return [TIME_AND_MATERIAL, FLAT_RATE, PROSPECTIVE, INTERNAL]
	}
	
	static ProjectBillCodeTypeList() {
		return [FLAT_RATE, TIME_AND_MATERIAL, PROSPECTIVE, INTERNAL]
	}
	
	public String toString() { 
		return this.getValue() 
	}
}
