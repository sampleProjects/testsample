package org.solcorp.etech

import java.util.Date;

import org.grails.databinding.BindingFormat

class ImportEmployeeJobLineRecord extends Auditable implements Serializable {
	
	Long id
	
	String hhEmployeeId
	
	String type
	
	String firstName
	
	String lastName
	
	String department
	
	Date lastHireDate
	
	Date createDate
	
	Date modifyDate
	
	String importStatus
	
	Date importStatusDate

	ImportEmployeeStatusType status
	
	JobRegister jobRegister
	
	Long employeeId
	
	String message
	
	static constraints = {
		type(nullable: true, blank: true)
		firstName(nullable: true, blank: true)
		lastName(nullable: true, blank: true)
		department(nullable: true, blank: true)
		lastHireDate(nullable: true, blank: true)
		createDate(nullable: true, blank: true)
		modifyDate(nullable: true, blank: true)
		importStatus(nullable: true, blank: true)
		importStatusDate(nullable: true, blank: true)
		status(nullable: true, blank: true)
		jobRegister(nullable: true, blank: true)
		employeeId(nullable: true, blank: true)
		message(nullable: true, blank: true)
	}

	static mapping = {
		
	}
	
	ImportEmployeeJobLineRecord(hhEMployeeMasterInstance, currentJob, employeeId, message, status, user) {
		this.hhEmployeeId = hhEMployeeMasterInstance.employeeId
		this.type = hhEMployeeMasterInstance.type
		this.firstName = hhEMployeeMasterInstance.firstName
		this.lastName = hhEMployeeMasterInstance.lastName
		this.department = hhEMployeeMasterInstance.department
		this.lastHireDate = hhEMployeeMasterInstance.lastHireDate
		this.createDate = hhEMployeeMasterInstance.createDate
		this.modifyDate = hhEMployeeMasterInstance.modifyDate
		this.importStatus = hhEMployeeMasterInstance.importStatus
		this.importStatusDate = hhEMployeeMasterInstance.importStatusDate
	
		this.jobRegister = currentJob
		this.employeeId = employeeId
		this.message = message
		this.status = status
		
		this.createdBy = user
		this.updatedBy = user
	}
}
