package org.solcorp.etech

import org.springframework.aop.aspectj.RuntimeTestWalker.ThisInstanceOfResidueTestVisitor;

/**
 * This domain is a temporary domain to track missing projects in the system while importing 
 * LaborTxnHistory records.
 * 
 * A new job will process these records to query PS views in order to import them if possible.
 */

class HHMissingProject extends Auditable implements Serializable {
	
	Long id
	
	String sourceTransactionId

	String transactionDate

	String employeeId

	String projectFullName

	String projectUnit

	String projectCustRevId

	String sourceProjectId

	String hours

	String dollarAmount

	HHMissingProjectStatusType status
	
	String message

	Project project
	
	JobRegister jobRegister
	
	static constraints = {
		sourceTransactionId(nullable: true, blank: true)
		transactionDate(nullable: true, blank: true)
		employeeId(nullable: true, blank: true)
		projectFullName(nullable: true, blank: true)
		projectUnit(nullable: true, blank: true)
		projectCustRevId(nullable: true, blank: true)
		sourceProjectId(nullable: false, unique: true, blank: true)
		hours(nullable: true, blank: true)
		dollarAmount(nullable: true, blank: true)
		message(nullable: true, blank: true)
		project(nullable: true)
	}

	static mapping = {
		
	}
	
	HHMissingProject(hhLaborTransactionInstance, project, message, status, jobRegisterInstance, user) {
		this.sourceTransactionId = hhLaborTransactionInstance.transactionId
		this.transactionDate = hhLaborTransactionInstance.transactionDate
		this.employeeId = hhLaborTransactionInstance.employeeId
		this.projectFullName = hhLaborTransactionInstance.projectFullName
		this.projectUnit = hhLaborTransactionInstance.projectUnit
		this.projectCustRevId = hhLaborTransactionInstance.projectCustRevId
		this.sourceProjectId = hhLaborTransactionInstance.projectId
		this.hours = hhLaborTransactionInstance.hours
		this.dollarAmount = hhLaborTransactionInstance.dollarAmount
		this.jobRegister = jobRegisterInstance
		this.project = project
		this.message = message
		this.status = status
		this.createdBy = user
		this.updatedBy = user
	}
	
	HHMissingProject(hhExpenseMsaterInstance, message, status, jobRegisterInstance, user) {
		this.sourceProjectId = hhExpenseMsaterInstance.projectId
		this.jobRegister = jobRegisterInstance
		this.message = message
		this.status = status
		this.createdBy = user
		this.updatedBy = user
	}
}
