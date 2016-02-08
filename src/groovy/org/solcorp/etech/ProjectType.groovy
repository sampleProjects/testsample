package org.solcorp.etech

public enum  ProjectType {

	
	
	UTILIZED(1,"Utilized"),
	
	NON_UTILIZED(2,"Non-Utilized"),
	
	SALES_SUPPORT(3,"Sales Support"),
	
	LEAVE(4,"Leave"),
	
	CORPORATE(5,"Corporate"),
	
	/*CUSTOMER(1, "Customer"),
	
	ADMINISTRATION(2, "Administration"),
	
	INTERNAL(3, "Internal"),
	
	MARKETING(4, "Marketing"),
		
	LEAVE(5, "Leave")*/	 
	
	private final String value
	private final Integer id
	
	ProjectType(Integer id, String value) {
		this.id = id
		this.value = value
	}
	
	public int getKey() {
		return id
	}

	public String getValue() {
		return value
	}
	
	static classTypelist() {
		//return [CUSTOMER, ADMINISTRATION, INTERNAL, MARKETING, LEAVE]
		return [UTILIZED, NON_UTILIZED, SALES_SUPPORT, LEAVE, CORPORATE]
	}
	
	public String toString() {
		return this.getValue()
	}
	
}
