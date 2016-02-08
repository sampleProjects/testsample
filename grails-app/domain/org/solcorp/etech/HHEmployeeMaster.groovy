package org.solcorp.etech

class HHEmployeeMaster implements Serializable {
		
	String employeeId
	
	String type
	
	String firstName
	
	String lastName
	
	String department
	
	Date lastHireDate
	
	Date createDate
	
	Date modifyDate
	
	String importStatus
	
	Date importStatusDate
		
	static constraints = {
		type(nullable: true)
		firstName(nullable: true)
		lastName(nullable: true)
		department(nullable: true)
	}
	
	static mapping = {
		table "employee_master"
		id composite: ['employeeId']
		version false
	}
}
