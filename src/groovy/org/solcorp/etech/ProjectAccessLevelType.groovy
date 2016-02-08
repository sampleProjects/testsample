package org.solcorp.etech

/**
 * This ENUM is used to store four possible values for the Project Access Level of any entity.
 * @author hjjogiya
 *
 */
public enum ProjectAccessLevelType {

 
	TIME_SHEET(1, "Time Sheet"),
	
	PROJECT_MANAGER(2, "Project Manager"),

	ACCOUNT_EXEC(3, "Account Executive"),

	ADMIN(4, "Admin")
	
	//C(5, "CUSTOMER")
	
	private final String value
	private final Integer id

	ProjectAccessLevelType(Integer id, String value) {
		this.id = id
		this.value = value
	}
	
	public int getKey() {
		return id
	}

	public String getValue() {
		return value
	}
	static projectAccessLevelList() {
		return [TIME_SHEET, PROJECT_MANAGER, ACCOUNT_EXEC, ADMIN]
	}
	static supervisorAccessRoleList() {
		return [PROJECT_MANAGER, ACCOUNT_EXEC, ADMIN]
	}
	public String toString() {
		return this.getValue()
	}
}
