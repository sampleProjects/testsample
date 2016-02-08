package org.solcorp.etech

import javax.persistence.Transient
import org.solcorp.etech.EmployeeType
import org.solcorp.etech.StatusType

class Employee extends Auditable {
	
	/**
	 * Used as primary key in all modules.
	 */
	 Long id
	
	/**
	 * Employee Code
	 */
	String code
	
	/**
	 * SS No
	 */
	//String ssn
	
	/**
	 * Employee Last Name
	 */
	String lastName
	
	/**
	 * Employee First Name
	 */
	String firstName
	
	/**
	 * Employee Middle Name
	 */
	String middleName 
	
	/**
	 * Labor Activity Dept 
	 */
	LaborActivityGroup laborActivityGroup
	
	/**
	 * Labor Activity Code 
	 */
	LaborActivityCode laborActivityCode
	 
	/** 
	 * StatusType ENUM
	 */
	StatusType status 

	/**
	 * EmployeeType ENUM
	 */
	EmployeeType employeeType
	
	/**
	 * Pay Dept 
	 */
	Department payDept
	
	/**
	 * Employee Supervisor
	 */
	Employee supervisor
	
	/**
	 * Acct Executive
	 */
	//Boolean isAccExecutive = false
	
	/**
	 * Email address
	 */
	String email
	
    static constraints = {
		code (nullable: false, blank: false, unique: true, maxSize: Constants.EMPLOYEE_CODE_LENGTH)
		//ssn(nullable: true, blank: true, maxSize: Constants.EMPLOYEE_SSNO_LENGTH)
		lastName(nullable: true, blank: true, maxSize: Constants.EMPLOYEE_LAST_NAME_LENGTH)
		firstName(nullable: false, blank: false, maxSize: Constants.EMPLOYEE_FIRST_NAME_LENGTH)
		middleName(nullable: true, blank: true, maxSize: Constants.EMPLOYEE_MIDDLE_NAME_LENGTH)
		laborActivityGroup(nullable: true)
		laborActivityCode(nullable: true)
		status(nullable: false)
		employeeType(nullable: false)
		//payDept(nullable: false, blank: false, maxSize: Constants.EMPLOYEE_PAY_DEPT_LENGTH)
		supervisor(nullable: true)
		email(nullable: true, email: true, maxSize: Constants.EMPLOYEE_EMAIL_LENGTH)
		dateCreated(nullable: true, display: false)		
		lastUpdated(nullable: true, display: false)
    }
	
	static mapping = {	
		status sqlType: 'varchar(30)'
		employeeType sqlType: 'varchar(30)'
	}
	
	@Transient
	static def createEmployeeFromHHEmployee(HHEmployeeMaster hhEmployeeInstance, User createdBy) {
		Employee employeeInstance = new Employee()
		
		employeeInstance.code = hhEmployeeInstance.employeeId
		employeeInstance.lastName = hhEmployeeInstance.lastName
		employeeInstance.firstName = hhEmployeeInstance.firstName
		employeeInstance.createdBy = createdBy
		employeeInstance.updatedBy = createdBy
		
		employeeInstance.status = StatusType.ACTIVE
		employeeInstance.employeeType = getEmployeeTypeFromHHEmployeeType(hhEmployeeInstance.type)	// Default Employee Type
		
		// Set PayDept
		employeeInstance.payDept = Department.findByCode(hhEmployeeInstance.department)
		if(! employeeInstance.payDept) {
			def deptList = Department.getDepartmentByCode(hhEmployeeInstance.department, hhEmployeeInstance.department?.isNumber())
			if(deptList.size() > 0) {
				employeeInstance.payDept = deptList.get(0)
			}
		}
		
		// employeeInstance.payDept = Department.findByCode(hhEmployeeInstance.department)
		return employeeInstance
	}
	
	@Transient
	static def getEmployeeTypeFromHHEmployeeType(type) {
		EmployeeType employeeType = EmployeeType.SALARY	// Default
		
		if(type) {
			if(type.equalsIgnoreCase(Constants.HH_EMPLOYEETYPE_HOURLY)) {
				employeeType = EmployeeType.HOURLY
			} else if (type.equalsIgnoreCase(Constants.HH_EMPLOYEETYPE_SALARY)) {
				employeeType = EmployeeType.SALARY
			} else if (type.equalsIgnoreCase(Constants.HH_EMPLOYEETYPE_CONTRACTOR)) {
				employeeType = EmployeeType.CONTRACT
			} 
		} 
		
		return employeeType
	}
	
	@Transient
	def isSystemJobUser() {
		def isSystemJobUser = false
		def systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		if(this.updatedBy?.id == systemJobUser?.id) {
			isSystemJobUser = true
		}
		return isSystemJobUser
	}
	
	@Transient
	def getEmployeeName() {
		def empName = "";
		if(this?.firstName){
			empName = this?.firstName
			if(this?.lastName){
				empName = empName + " " + this?.lastName
			}
		}
		return empName
	}
}
