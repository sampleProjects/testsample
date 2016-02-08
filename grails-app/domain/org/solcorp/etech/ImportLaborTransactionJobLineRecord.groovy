package org.solcorp.etech

class ImportLaborTransactionJobLineRecord extends Auditable implements Serializable {
	
	Long id
	
	String sourceTransactionId

	String transactionDate

	String employeeId

	String projectFullName

	String projectUnit

	String projectCustRevId

	String projectId

	String hours

	String dollarAmount

	String importStatus

	String importStatusDate

	ImportLaborTransactionStatusType status
	
	JobRegister jobRegister
	
	Long transactionId
	
	String message
	
	static constraints = {
		sourceTransactionId(nullable: true, blank: true)
		transactionDate(nullable: true, blank: true)
		employeeId(nullable: true, blank: true)
		projectFullName(nullable: true, blank: true)
		projectUnit(nullable: true, blank: true)
		projectCustRevId(nullable: true, blank: true)
		projectId(nullable: true, blank: true)
		hours(nullable: true, blank: true)
		dollarAmount(nullable: true, blank: true)
		importStatus(nullable: true, blank: true)
		importStatusDate(nullable: true, blank: true)
		transactionId(nullable: true, blank: true)
		message(nullable: true, blank: true)
	}

	static mapping = {
		
	}
	
	ImportLaborTransactionJobLineRecord(hhLaborTransactionInstance, jobRegisterInstance, transactionId, message, status, user) {
		this.sourceTransactionId = hhLaborTransactionInstance.transactionId
		this.transactionDate = hhLaborTransactionInstance.transactionDate
		this.employeeId = hhLaborTransactionInstance.employeeId
		this.projectFullName = hhLaborTransactionInstance.projectFullName
		this.projectUnit = hhLaborTransactionInstance.projectUnit
		this.projectCustRevId = hhLaborTransactionInstance.projectCustRevId
		this.projectId = hhLaborTransactionInstance.projectId
		this.hours = hhLaborTransactionInstance.hours
		this.dollarAmount = hhLaborTransactionInstance.dollarAmount
		this.importStatus = hhLaborTransactionInstance.importStatus
		this.importStatusDate = hhLaborTransactionInstance.importStatusDate
		this.jobRegister = jobRegisterInstance
		this.transactionId = transactionId
		this.message = message
		this.status = status
		this.createdBy = user
		this.updatedBy = user
	}
}
