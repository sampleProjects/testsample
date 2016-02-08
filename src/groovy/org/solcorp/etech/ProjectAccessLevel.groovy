package org.solcorp.etech

/**
 * This ENUM is used to store four possible values for the Project Access Level of any entity.
 * @author hjjogiya
 *
 */
public enum ProjectAccessLevel {

 
	TIME_SHEET(1,"TIME SHEET"),
	
	PROJECT_MANAGER(2, "PROJECT MANAGER"),

	ACCOUNT_EXEC(3, "ACCOUNT EXEC"),

	ADMIN(4, "ADMIN")
	
	//C(5, "CUSTOMER")
	
	private final String value
	private final Integer id

	ProjectAccessLevel(Integer id, String value) {
		this.id=id
		this.value = value
	}
	
	public int getKey() {
		return id
	}

	public String getValue() {
		return value
	}
	static projectAccessLevelList() {
		[TIME_SHEET, PROJECT_MANAGER, ACCOUNT_EXEC, ADMIN]
	}
	public String toString() {
		return this.getValue()
	}
}
