package org.solcorp.etech

import java.math.RoundingMode

import org.grails.databinding.BindingFormat
import org.solcorp.etech.utils.DateFormatUtils

class ProjectActualLaborDetail extends Auditable {

	Long id
	
	Project project
	
	Customer customer
	
	Employee employee
	
	LaborActivityCode laborActivityCode
	
	BigDecimal standardRate
	
	BigDecimal standardCost
	
	BigDecimal actualRate
	
	BigDecimal actualCost
	
	BigDecimal overHeadPercentage
	
	BigDecimal standardOverheadCost
	
	BigDecimal actualOverheadCost
	
	BigDecimal standardTotalCost
	
	BigDecimal actualTotalCost
	
	//-----------------------------------------------------------	
	// Copy of LaborTransaction Details
	//-----------------------------------------------------------
	
	String sourceTransactionId		// Mapped with transactionId
	
	@BindingFormat("MM/dd/yyyy")
	Date transactionDate
	
	String hhEmployeeId				// Mapped with employeeId
	
	String projectFullName
	
	String businessUnit				// Mapped with projectUnit
	
	String projectCustRevId
	
	String hhProjectId				// Mapped with projectId
	
	BigDecimal hours
	
	BigDecimal dollarAmount
	
	static belongsTo = [projectLabor: ProjectActualLabor]
	
    static constraints = {
		hours (nullable: false, scale: 4)
		standardRate (nullable: false, scale: 4)
		standardCost (nullable: false, scale: 4)
		overHeadPercentage (nullable: false, scale: 4)
		standardOverheadCost (nullable: false, scale: 4)
		actualOverheadCost (nullable: false, scale: 4)
		standardTotalCost (nullable: false, scale: 4)
		actualTotalCost (nullable: false, scale: 4)
		
		sourceTransactionId(nullable: false, blank: false)
		transactionDate(nullable: false, blank: false)
		hhEmployeeId(nullable: false, blank: false)
		projectFullName(nullable: true, blank: true)
		businessUnit(nullable: true, blank: true)
		projectCustRevId(nullable: true, blank: true)
		hhProjectId(nullable: false, blank: false)
		dollarAmount(nullable: false, scale: 4)
    }
	
	ProjectActualLaborDetail(hhLaborTransactionInstance, projectInstance, customerInstance, employeeInstance, laborActivityCode, user) {
		this.project = projectInstance
		this.customer = customerInstance
		this.employee = employeeInstance
		this.laborActivityCode = laborActivityCode
		
		this.standardRate = laborActivityCode.standardRate ?: BigDecimal.ZERO
		this.hours = new BigDecimal(hhLaborTransactionInstance.hours) ?: BigDecimal.ZERO
		
		this.standardCost = this.hours.multiply(this.standardRate).setScale(4, RoundingMode.UP)
		this.dollarAmount = new BigDecimal(hhLaborTransactionInstance.dollarAmount) ?: BigDecimal.ZERO
		
		this.actualRate = this.hours > 0 ? this.dollarAmount.divide(this.hours, 4, RoundingMode.UP) : BigDecimal.ZERO
		this.actualCost = this.dollarAmount
		
		this.overHeadPercentage = laborActivityCode.overHead ?: BigDecimal.ZERO
		
		this.standardOverheadCost = this.overHeadPercentage.multiply(this.standardCost).setScale(4, RoundingMode.UP).divide(BigDecimal.valueOf(100), 4, RoundingMode.UP)
		this.actualOverheadCost = this.overHeadPercentage.multiply(this.actualCost).setScale(4, RoundingMode.UP).divide(BigDecimal.valueOf(100), 4, RoundingMode.UP)
		
		this.standardTotalCost = this.standardCost.add(this.standardOverheadCost)
		this.actualTotalCost = this.actualCost.add(this.actualOverheadCost)
		
		this.sourceTransactionId = hhLaborTransactionInstance.transactionId
		this.transactionDate = DateFormatUtils.getDateFromString(DateFormatUtils.dateConvertFromString(hhLaborTransactionInstance.transactionDate, Constants.DATE_FORMAT, "yyyy-MM-dd HH:mm:ss"))
		this.hhEmployeeId = hhLaborTransactionInstance.employeeId
		this.projectFullName = hhLaborTransactionInstance.projectFullName
		this.businessUnit = hhLaborTransactionInstance.projectUnit
		this.projectCustRevId = hhLaborTransactionInstance.projectCustRevId
		this.hhProjectId = hhLaborTransactionInstance.projectId
		this.createdBy = user
		this.updatedBy = user
	}
}
