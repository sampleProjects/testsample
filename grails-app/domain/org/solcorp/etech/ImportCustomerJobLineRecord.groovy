package org.solcorp.etech

import org.grails.databinding.BindingFormat

class ImportCustomerJobLineRecord extends Auditable implements Serializable {
	
	Long id
	
	String hhCustId
	
	String name1
	
	String hhMasterCustId
	
	String custStatus
	
	@BindingFormat("dd-MON-yy")
	Date dateAdded
	
	@BindingFormat("dd-MON-yy")
	Date lastMaintDttm
	
	@BindingFormat("dd-MON-yy")
	Date dttm_stamp
	
	String descrshort

	ImportCustomerStatusType status
	
	JobRegister jobRegister
	
	Long customerId
	
	String message
	
	static constraints = {
		name1(nullable: true, blank: true)
		hhMasterCustId(nullable: true, blank: true)
		custStatus(nullable: true, blank: true)
		dateAdded(nullable: true, blank: true)
		lastMaintDttm(nullable: true, blank: true)
		dttm_stamp(nullable: true, blank: true)
		status(nullable: true, blank: true)
		jobRegister(nullable: true, blank: true)
		customerId(nullable: true, blank: true)
		message(nullable: true, blank: true)
	}

	static mapping = {
		
	}
	
	ImportCustomerJobLineRecord(hhCustomerMasterInstance, currentJob, customerId, message, status, user) {
		this.hhCustId = hhCustomerMasterInstance.hhCustId
		this.name1 = hhCustomerMasterInstance.name1
		this.hhMasterCustId = hhCustomerMasterInstance.hhMasterCustId
		this.custStatus = hhCustomerMasterInstance.custStatus
		this.dateAdded = hhCustomerMasterInstance.dateAdded
		this.lastMaintDttm = hhCustomerMasterInstance.lastMaintDttm
		this.dttm_stamp = hhCustomerMasterInstance.dttm_stamp
		this.descrshort = hhCustomerMasterInstance.descrshort

		this.jobRegister = currentJob
		this.customerId = customerId
		this.message = message
		this.status = status
		
		this.createdBy = user
		this.updatedBy = user
	}
}
