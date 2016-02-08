package org.solcorp.etech

import org.grails.databinding.BindingFormat

class ProjectActualExpenseDetail extends Auditable {
	
	Long id
	
	Project project
	
	Expense expense
	
	//-----------------------------------------------------------	
	// Copy of HH Expense Master Details
	//-----------------------------------------------------------
	
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
	/* Items being copied from the view */
	
    static constraints = {
    }
	
	static mapping = {
		unique: ['hhProjectId', 'journalId', 'journalLine']
	}
	
	static belongsTo = [projectActualExpense: ProjectActualExpense]
	
	ProjectActualExpenseDetail(hhExpenseMasterInstance, projectInstance, expenseObj, user) {
		/*this.project = projectInstance
		this.expense = expenseObj
		this.businessUnit = hhExpenseMasterInstance[0]
		this.journalId = hhExpenseMasterInstance[1]
		this.journalDate = hhExpenseMasterInstance[2]
		this.unpostSeq = hhExpenseMasterInstance[3]
		this.fiscalYear = hhExpenseMasterInstance[4]
		this.accountingPeriod = hhExpenseMasterInstance[5]
		this.source = hhExpenseMasterInstance[6]
		this.jrnlHdrStatus = hhExpenseMasterInstance[7]
		this.postedDate = hhExpenseMasterInstance[8]
		this.descr = hhExpenseMasterInstance[9]
		this.currencyCd = hhExpenseMasterInstance[10]
		this.jrnlCreateDttm = hhExpenseMasterInstance[11]
		this.journalDateOrig = hhExpenseMasterInstance[12]
		this.journalLine = hhExpenseMasterInstance[13]
		this.account = hhExpenseMasterInstance[14]
		this.deptid = hhExpenseMasterInstance[15]
		this.operatingUnit = hhExpenseMasterInstance[16]
		this.product = hhExpenseMasterInstance[17]
		this.hhProjectId = hhExpenseMasterInstance[18]
		this.monetaryAmount = hhExpenseMasterInstance[19]
		this.foreignCurrency = hhExpenseMasterInstance[20]
		this.foreignAmount = hhExpenseMasterInstance[21]
		this.voucherId = hhExpenseMasterInstance[22]
		this.invoiceId = hhExpenseMasterInstance[23]
		this.vendorId = hhExpenseMasterInstance[24]
		this.name1 = hhExpenseMasterInstance[25]*/
		
		this.project = projectInstance
		this.expense = expenseObj
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
		
		this.createdBy = user
		this.updatedBy = user
	}
}
