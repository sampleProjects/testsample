package org.solcorp.etech

public enum HHMissingProjectStatusType {

	// Initial Status
 	NOT_FOUND_IN_HHPC(1, "Project Not Found In HHPC"),
	
	 
	// Status if project not found in PS View
	NOT_FOUND_IN_PS_VIEW(2, "Project Not Found In PS View"),
	
	
	// Status if project found in PS View and imported successfully in HHPC
	IMPORTED_FROM_PS_VIEW_TO_HHPC(3, "Project Not Found In PS View")
	
	private final String value
	private final Integer id

	HHMissingProjectStatusType(Integer id, String value) {		
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
		return [NOT_FOUND_IN_HHPC, NOT_FOUND_IN_PS_VIEW, IMPORTED_FROM_PS_VIEW_TO_HHPC]
	}
}