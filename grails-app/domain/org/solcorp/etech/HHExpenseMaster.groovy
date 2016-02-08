package org.solcorp.etech

import java.util.Date;

import javax.persistence.Transient;

import org.grails.databinding.BindingFormat

class HHExpenseMaster implements Serializable {

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
	
	String projectId
	
	BigDecimal monetaryAmount
	
	String foreignCurrency
	
	BigDecimal foreignAmount
	
	String voucherId
	
	String invoiceId
	
	String vendorId
	
	String name1
	
	Date dttm_stamp
	
	String descrshort
	
    static constraints = {
    }
	
	static mapping = {
		if (grails.util.Environment.current.name == "hh_stage" || grails.util.Environment.current.name == "hh_prod") {
			datasource 'hh'
			id composite: ['businessUnit', 'journalId', 'journalDate', 'unpostSeq', 'fiscalYear', 'accountingPeriod', 'projectId', 'journalLine']
		}
		
		//table "ps_hhpc_costexp_vw"
		table "PS_HHPC_COSTXP_TBL"
		version false
	 }
	
	@Transient
	def static getHHExpenseMonetaryAmountTotal(projectCode) {
		println "HHExpenseMaster.getHHExpenseMonetaryAmountTotal - START"
		
		BigDecimal monetaryAmount = 0.00

		// Commented Temporarily - START
		/*def hhExpenseMasterCriteria = HHExpenseMaster.createCriteria()
		def hhExpenseMasterSum = hhExpenseMasterCriteria.list() {
			projections{ sum("monetaryAmount") }
			and {
				eq("projectId", projectCode)
			}
		}
		
		println "hhExpenseMasterSum: " + hhExpenseMasterSum?.size()
		
		if(hhExpenseMasterSum?.size() > 0) {
			if(hhExpenseMasterSum[0] != null) {
				monetaryAmount = hhExpenseMasterSum[0]
			}
		}*/
		// Commented Temporarily - END
		
		println "monetaryAmount: " + monetaryAmount
		
		println "HHExpenseMaster.getHHExpenseMonetaryAmountTotal - END"
		return monetaryAmount
	}
	
	HHExpenseMaster(String businessUnit, String journalId, Date journalDate,
	Long unpostSeq, Long fiscalYear, Long accountingPeriod, String source, String jrnlHdrStatus, Date postedDate,
	String descr, String currencyCd, Date jrnlCreateDttm, Date journalDateOrig, Long journalLine,
	String account, String deptid, String operatingUnit, String product,
	String projectId, BigDecimal monetaryAmount, String foreignCurrency, BigDecimal foreignAmount,
	String voucherId, String invoiceId, String vendorId, String name1, Date dttm_stamp,
	String descrshort) {

		this.businessUnit = businessUnit
		this.journalId = journalId
		this.journalDate = journalDate
		this.unpostSeq = unpostSeq
		this.fiscalYear = fiscalYear
		this.accountingPeriod = accountingPeriod
		this.source = source
		this.jrnlHdrStatus = jrnlHdrStatus
		this.postedDate = postedDate
		this.descr = descr
		this.currencyCd = currencyCd
		this.jrnlCreateDttm = jrnlCreateDttm
		this.journalDateOrig = journalDateOrig
		this.journalLine = journalLine
		this.account = account
		this.deptid = deptid
		this.operatingUnit = operatingUnit
		this.product = product
		this.projectId = projectId
		this.monetaryAmount = monetaryAmount
		this.foreignCurrency = foreignCurrency
		this.foreignAmount = foreignAmount
		this.voucherId = voucherId
		this.invoiceId = invoiceId
		this.vendorId = vendorId
		this.name1 = name1
		this.dttm_stamp = dttm_stamp
		this.descrshort = descrshort
	}
}
