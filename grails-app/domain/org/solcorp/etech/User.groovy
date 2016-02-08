package org.solcorp.etech
import javax.persistence.Transient
/**
 * This class is used to store user information
 * @author hjjogiya
 *
 */
class User extends Auditable {
	
	/**
	 * Used as a UUID PK
	 */
	Long id	
    
	/**
	 * User Name
	 */
	String username
    
	/**
	 * Sha256Hash Password
	 */
	String passwordHash
    
	/**
	 * ENUM User Status Type ACTIVE, INACTIVE
	 */
	StatusType status
	
	/**
	 * Employee Report Type 
	 */
	EmployeeReportType empReportType
	
	/**
	 * Employee Supervisor (Approves Timesheets) : YES/NO
	 */
	String tsApprove
	
	/**
	 * User must change password at next login flag
	 */
	Boolean isPasswordChangeRequired = false 
		
	/**
	 * Deleted Flag
	 */
	Boolean isDeleted = false
	
	Employee employee
	
	/**
	 * Hours Only Flag
	 */
	Boolean hoursOnly = false 
	
	@Transient
	String employeeTxt
	
    static hasMany = [roles: Role, permissions: String]

	static transients = ['employeeTxt']
	
    static constraints = {
        username(nullable: false, blank: false, unique: true)
		passwordHash(nullable: false, blank: false)
		status(nullable: false)		
		isPasswordChangeRequired(nullable: true)
		tsApprove(nullable: false, blank: false)		
		empReportType(nullable: false)
		employee(nullable: true)
		hoursOnly(nullable: true)
		dateCreated(nullable: true, display: false)
		lastUpdated(nullable: true, display: false)
    }
	
	static mapping = {
		table "appuser"
		status sqlType: 'varchar(30)'
		empReportType sqlType: 'varchar(30)'
	}
}
