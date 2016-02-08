package org.solcorp.etech

import java.util.Date;

import org.grails.databinding.BindingFormat

class ImportProjectJobLineRecord extends Auditable implements Serializable {
	
	Long id
	
	String hhProjectId
	
	String businessUnit
	
	String descr
	
	String custId
	
	String hhCustId
	
	String name1
	
	String effStatus
	
	Date startDt
	
	Date endDt
	
	String salesPerson
	
	String name2
	
	String descrshort
	
	Date dttmStamp

	ImportProjectStatusType status
	
	JobRegister jobRegister
	
	Long projectId
	
	String message
	
	static constraints = {
		
		businessUnit(nullable: true, blank: true)
		descr(nullable: true, blank: true)
		custId(nullable: true, blank: true)
		hhCustId(nullable: true, blank: true)
		name1(nullable: true, blank: true)
		effStatus(nullable: true, blank: true)
		startDt(nullable: true, blank: true)
		endDt(nullable: true, blank: true)
		salesPerson(nullable: true, blank: true)
		name2(nullable: true, blank: true)
		descrshort(nullable: true, blank: true)
		dttmStamp(nullable: true, blank: true)
		status(nullable: true, blank: true)
		jobRegister(nullable: true, blank: true)
		projectId(nullable: true, blank: true)
		message(nullable: true, blank: true)
	}

	static mapping = {
		
	}
	
	ImportProjectJobLineRecord(hhProjectMasterInstance, currentJob, projectId, message, status, user) {
		this.hhProjectId = hhProjectMasterInstance.projectId
		this.businessUnit = hhProjectMasterInstance.businessUnit 
		this.descr = hhProjectMasterInstance.descr
		this.custId = hhProjectMasterInstance.custId
		this.hhCustId = hhProjectMasterInstance.hhCustId
		this.name1 = hhProjectMasterInstance.name1
		this.effStatus = hhProjectMasterInstance.effStatus
		this.startDt = hhProjectMasterInstance.startDt
		this.endDt = hhProjectMasterInstance.endDt
		this.salesPerson = hhProjectMasterInstance.salesPerson
		this.name2 = hhProjectMasterInstance.name2
		this.descrshort = hhProjectMasterInstance.descrshort
		this.dttmStamp = hhProjectMasterInstance.dttmStamp
		
		this.jobRegister = currentJob
		this.projectId = projectId
		this.message = message
		this.status = status
		
		this.createdBy = user
		this.updatedBy = user
	}
}
