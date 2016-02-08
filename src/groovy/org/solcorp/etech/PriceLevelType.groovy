package org.solcorp.etech

public enum PriceLevelType {
	
	ONE(1, "1"),
	TWO(2, "2"),
	THREE(3, "3"),
	FOUR(4, "4")
	
	private final String value
	private final String id
	
	public PriceLevelType(Integer id, String value) {
		this.id = id
		this.value = value
	}
	
	public int getKey() {
		return id
	}
	
	public String getValue() {
		return value
	}
	
	static priceLevelList() {
		return [ONE, TWO, THREE, FOUR]
	}
	
	public String toString() { 
		return this.getValue() 
	}
}
