package org.solcorp.etech

import javax.persistence.Transient;

class Department extends Auditable {

	/**
	 *  Department Id 
	 */
	Long id

	/**
	 * Department Code 
	 */
	String code
	
	
	/**
	 * Labor Activity Group
	 */
	LaborActivityGroup laborActivityGroup
	
	/**
	 * Description of department 
	 */
	String description 
	
	@Transient
	String laborActivityGrouptxt
	
	static constraints = {
		code (nullable: false, blank: false, unique: true, maxSize: Constants.DEPARTMENT_CODE_LENGTH)		
		laborActivityGroup(nullable: true)
		description(nullable: true)
    }
	static transients = ['laborActivityGrouptxt']
		
	static mapping = {
	}
	
	@Transient
	static def createDepartmentFromHHEmployee(HHEmployeeMaster hhEmployeeInstance, User createdBy) {
		
		Department departmentInstance = new Department()
		
		if(hhEmployeeInstance.department.isNumber() 
			&& hhEmployeeInstance.department.length() < 6) {
			departmentInstance.code = hhEmployeeInstance.department.padLeft(6, '0')
		} else {
			departmentInstance.code = hhEmployeeInstance.department
		}
		
		departmentInstance.createdBy = createdBy
		departmentInstance.updatedBy = createdBy
		
		return departmentInstance
	}
	
	@Transient
	static def getDepartmentByCode(code, numericComparision) {
		/*log.info "getDepartmentByCode @ DepartmentService Start"*/
		
		def departmentCriteria = Department.createCriteria()
		def departmentInstanceList = departmentCriteria.list() {
			 
			and {
				if(numericComparision) {
					sqlRestriction "ISNUMERIC(code) = 1"
					sqlRestriction "SUBSTRING(code, PATINDEX('%[^0 ]%', code + 'a'), LEN(code)) = " + code
				} else {
					eq("code", code)
				}
			}
		}
		
		/*log.info "getDepartmentByCode @ DepartmentService End"*/
		return departmentInstanceList
	}
}
