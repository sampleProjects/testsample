package org.solcorp.etech

import javax.persistence.Transient
import org.solcorp.etech.EmployeeType
import org.solcorp.etech.StatusType

class EmployeeActivityCode extends Auditable {
	
	/**
	 * Used as primary key in all modules.
	 */
	 Long id
	
	/**
	 * Employee Code
	 */
	String employeeCode
	
	/**
	 * Activity Code
	 */
	String activityCode
	
	/*
	 * EMPLOYEE_NOT_FOUND
	 * ACTIVITY_NOT_FOUND
	 * ACTIVITY_ASSIGNED
	 * LOADED
	 */
	String status
	
	static constraints = {
		employeeCode (nullable: false, blank: false, unique: true, maxSize: Constants.EMPLOYEE_CODE_LENGTH)
		activityCode (nullable: false, blank: false, maxSize: Constants.LABORACTIVITYCODE_CODE_LENGTH)

		dateCreated(nullable: true, display: false)		
		lastUpdated(nullable: true, display: false)
    }
	
	static mapping = {	
	}
}
