package org.solcorp.etech

public enum Pagination {
		
	FIFTY(1, "50"),
	
	HUNDRED(2, "100"),
	
	FIVE_HUNDRED(3, "500"),
	
	ONE_THOUSAND(4, "1000"),
	
	FIVE_THOUSAND(5, "5000")

	private final String value
	private final Integer id

	Pagination(Integer id, String value) {
		this.id = id
		this.value = value
	}

	public int getKey() {
		return id
	}

	public String getValue() {
		return value
	}

	public String toString() {
		return this.getValue()
	}
	
	static list() {
		return [FIFTY, HUNDRED, FIVE_HUNDRED, ONE_THOUSAND, FIVE_THOUSAND]
	}
}
