package org.solcorp.etech

import org.grails.databinding.BindingFormat

class ImportExpenseJobLineRecord extends Auditable implements Serializable {
	
	Long id
	
	String businessUnit
	
	String journalId
	
	@BindingFormat("MM/dd/yyyy")
	Date journalDate
	
	Long unpostSeq
	
	Long fiscalYear
	
	Long accountingPeriod
	
	String source
	
	String jrnlHdrStatus
	
	@BindingFormat("MM/dd/yyyy")
	Date postedDate
	
	String descr
	
	String currencyCd
	
	@BindingFormat("MM/dd/yyyy")
	Date jrnlCreateDttm
	
	@BindingFormat("MM/dd/yyyy")
	Date journalDateOrig
	
	Long journalLine
	
	String account
	
	String deptid
	
	String operatingUnit
	
	String product
	
	String hhProjectId
	
	BigDecimal monetaryAmount
	
	String foreignCurrency
	
	BigDecimal foreignAmount
	
	String voucherId
	
	String invoiceId
	
	String vendorId
	
	String name1
	
	Date dttm_stamp
	
	String descrshort

	ImportExpenseStatusType status
	
	JobRegister jobRegister
	
	Long expenseId
	
	String message
	
	static constraints = {
		
		businessUnit(nullable: true, blank: true)
		journalId(nullable: true, blank: true)
		journalDate(nullable: true, blank: true)
		unpostSeq(nullable: true, blank: true)
		fiscalYear(nullable: true, blank: true)
		accountingPeriod(nullable: true, blank: true)
		source(nullable: true, blank: true)
		jrnlHdrStatus(nullable: true, blank: true)
		postedDate(nullable: true, blank: true)
		descr(nullable: true, blank: true)
		currencyCd(nullable: true, blank: true)
		jrnlCreateDttm(nullable: true, blank: true)
		journalDateOrig(nullable: true, blank: true)
		journalLine(nullable: true, blank: true)
		account(nullable: true, blank: true)
		deptid(nullable: true, blank: true)
		operatingUnit(nullable: true, blank: true)
		product(nullable: true, blank: true)
		hhProjectId(nullable: true, blank: true)
		monetaryAmount(nullable: true, blank: true)
		foreignCurrency(nullable: true, blank: true)
		foreignAmount(nullable: true, blank: true)
		voucherId(nullable: true, blank: true)
		invoiceId(nullable: true, blank: true)
		vendorId(nullable: true, blank: true)
		name1(nullable: true, blank: true)
		dttm_stamp(nullable: true, blank: true)
		descrshort(nullable: true, blank: true)
		status(nullable: true, blank: true)
		jobRegister(nullable: true, blank: true)
		expenseId(nullable: true, blank: true)
		message(nullable: true, blank: true)
	}

	static mapping = {
		
	}
	
	ImportExpenseJobLineRecord(hhExpenseMasterInstance, currentJob, expenseId, message, status, user) {
		
		this.businessUnit = hhExpenseMasterInstance.businessUnit
		this.journalId = hhExpenseMasterInstance.journalId
		this.journalDate = hhExpenseMasterInstance.journalDate
		this.unpostSeq = hhExpenseMasterInstance.unpostSeq
		this.fiscalYear = hhExpenseMasterInstance.fiscalYear
		this.accountingPeriod = hhExpenseMasterInstance.accountingPeriod
		this.source = hhExpenseMasterInstance.source
		this.jrnlHdrStatus = hhExpenseMasterInstance.jrnlHdrStatus
		this.postedDate = hhExpenseMasterInstance.postedDate
		this.descr = hhExpenseMasterInstance.descr
		this.currencyCd = hhExpenseMasterInstance.currencyCd
		this.jrnlCreateDttm = hhExpenseMasterInstance.jrnlCreateDttm
		this.journalDateOrig = hhExpenseMasterInstance.journalDateOrig
		this.journalLine = hhExpenseMasterInstance.journalLine
		this.account = hhExpenseMasterInstance.account
		this.deptid = hhExpenseMasterInstance.deptid
		this.operatingUnit = hhExpenseMasterInstance.operatingUnit
		this.product = hhExpenseMasterInstance.product
		this.hhProjectId = hhExpenseMasterInstance.projectId
		this.monetaryAmount = hhExpenseMasterInstance.monetaryAmount
		this.foreignCurrency = hhExpenseMasterInstance.foreignCurrency
		this.foreignAmount = hhExpenseMasterInstance.foreignAmount
		this.voucherId = hhExpenseMasterInstance.voucherId
		this.invoiceId = hhExpenseMasterInstance.invoiceId
		this.vendorId = hhExpenseMasterInstance.vendorId
		this.name1 = hhExpenseMasterInstance.name1
		
		this.jobRegister = currentJob
		this.expenseId = expenseId
		this.message = message
		this.status = status
		
		this.createdBy = user
		this.updatedBy = user
	}
}
