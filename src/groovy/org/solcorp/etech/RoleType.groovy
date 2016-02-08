package org.solcorp.etech

public enum RoleType {
	ROLE_ADMIN(1, "Admin"),
	
	ROLE_SUPER_ADMIN(2, "Super Admin"),

	ROLE_SYSTEM_JOB(3, "System Job"),

	ROLE_TIMESHEET(4, "Timesheet"),
	
	ROLE_ACCOUNT_DIRECTOR(5, "Account Director"),
	
	ROLE_EXECUTIVE_LEADERS(6, "Executive Leaders"),

	ROLE_OPERATIONS_LEADERS(7, "Operations Leaders"),

	ROLE_PROJECT_MANAGER(8, "Project Manager"),
	
	ROLE_FINANCE(9, "Finance"),
	
	ROLE_PROJECT_COSTING(10, "Project Costing"),

	ROLE_SALES(11, "Sales"),

	ROLE_EMPLOYEE_SUPERVISOR(12, "Employee Supervisor")
	
	private final String value
	private final Integer id

	RoleType(Integer id, String value) {
		this.id = id
		this.value = value
	}
	
	public int getKey() {
		return id
	}

	public String getValue() {
		return value
	}
	
	/*static adminRoleList() {
		return [ROLE_ADMIN, ROLE_TIMESHEET, ROLE_ACCOUNT_DIRECTOR, ROLE_EXECUTIVE_LEADERS,
		ROLE_OPERATIONS_LEADERS, ROLE_PROJECT_MANAGER, ROLE_FINANCE, ROLE_PROJECT_COSTING, ROLE_SALES]
	}*/
	
	static etpAdminRoleList() {
		return [ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_TIMESHEET, ROLE_ACCOUNT_DIRECTOR, ROLE_EXECUTIVE_LEADERS,
		ROLE_OPERATIONS_LEADERS, ROLE_PROJECT_MANAGER, ROLE_FINANCE, ROLE_PROJECT_COSTING, ROLE_SALES]
	}
	
	static roleList() {
		def roleList = roleListForPermissions()
		roleList.add(ROLE_ADMIN)
		
		return roleList
	}
	
	static roleListForPermissions() {
		return [ROLE_TIMESHEET, ROLE_ACCOUNT_DIRECTOR, ROLE_EXECUTIVE_LEADERS, ROLE_OPERATIONS_LEADERS, 
			ROLE_PROJECT_MANAGER, ROLE_FINANCE, ROLE_PROJECT_COSTING, ROLE_SALES]
	}
	
	static allRoleList() {
		return [ROLE_ADMIN, ROLE_SUPER_ADMIN, ROLE_SYSTEM_JOB, ROLE_TIMESHEET, ROLE_ACCOUNT_DIRECTOR, ROLE_EXECUTIVE_LEADERS,
		ROLE_OPERATIONS_LEADERS, ROLE_PROJECT_MANAGER, ROLE_FINANCE, ROLE_PROJECT_COSTING, ROLE_SALES, ROLE_EMPLOYEE_SUPERVISOR]
	}
	
	static supervisorRoleList() {
		return [ROLE_ADMIN, ROLE_PROJECT_MANAGER, ROLE_ACCOUNT_DIRECTOR]
	}
	
	static adminRoleList() {
		return [ROLE_ADMIN, ROLE_SUPER_ADMIN]
	}
	
	public String toString() {
		return this.getValue()
	}
}
