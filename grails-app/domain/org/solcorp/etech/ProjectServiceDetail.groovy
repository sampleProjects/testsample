package org.solcorp.etech

import java.math.BigDecimal;

import javax.persistence.Transient;

import org.grails.databinding.BindingFormat;

class ProjectServiceDetail   {
	
	Long id
	
	@BindingFormat("MM/dd/yyyy")
	Date scheduleStartDate
	
	@BindingFormat("MM/dd/yyyy")
	Date scheduleCompDate
	
	@BindingFormat("MM/dd/yyyy")
	Date reviseStartDate
	
	@BindingFormat("MM/dd/yyyy")
	Date reviseCompDate
	
	@BindingFormat("MM/dd/yyyy")
	Date actualStartDate
	
	@BindingFormat("MM/dd/yyyy")
	Date actualCompDate
	
	Service service
	 
	ProjectLabor plannedLabor
	
	ProjectExpense plannedExpense
	
	ProjectActualLabor actualLabor
	
	ProjectActualExpense actualExpense
	
	static belongsTo = [projectProductDetail: ProjectProductDetail]
		
	
	@Transient
	int index
	
	@Transient
	BigDecimal plannedHoursTotal
	
	@Transient
	BigDecimal actualHoursTotal
	
	@Transient
	BigDecimal varianceHoursTotal
	
	@Transient
	BigDecimal plannedLaborTotal
	@Transient
	BigDecimal actualLaborTotal
	
	@Transient
	BigDecimal plannedExpenseTotal
	
	@Transient
	BigDecimal actualExpenseTotal
	
	@Transient
	BigDecimal varianceLaborTotal
	
	@Transient
	BigDecimal varianceExpenseTotal
	
	@Transient
	BigDecimal varianceOHCostTotal
	
	@Transient
	BigDecimal actualStdOHCostTotal
	
	@Transient
	BigDecimal plannedStdOHCostTotal
	@Transient
	BigDecimal plannedTotal
	@Transient
	BigDecimal actualTotal
	@Transient
	BigDecimal varianceTotal
	static transients = ['index', 'plannedHoursTotal','actualHoursTotal','varianceHoursTotal','plannedLaborTotal','actualLaborTotal','plannedExpenseTotal','actualExpenseTotal'
		,'varianceLaborTotal','varianceExpenseTotal','varianceOHCostTotal','actualStdOHCostTotal','plannedStdOHCostTotal','plannedTotal','actualTotal','varianceTotal']
		
    static constraints = {	
		scheduleStartDate(nullable: true, blank: true)
		scheduleCompDate(nullable: true, blank: true)		
		reviseStartDate(nullable: true, blank: true)		
		reviseCompDate(nullable: true, blank: true)		
		actualStartDate(nullable: true, blank: true)		
		actualCompDate(nullable: true, blank: true)
		service(nullable: false)
		plannedLabor(nullable: true)
		actualLabor(nullable: true)
		plannedExpense(nullable: true)
		actualExpense(nullable: true)
    }
	
	@Transient
	def getServiceSummary() {
		//Calculate Planned Labor and Planned Expense
		this.plannedLaborTotal = this.getAllPlannedLaborTotal()
		this.plannedExpenseTotal = this.getAllPlannedExpenseTotal()
		this.plannedStdOHCostTotal = this.getAllPlannedStdOHCostTotal()
		
		this.plannedTotal = this?.plannedLaborTotal.add(this?.plannedExpenseTotal).add(this.plannedStdOHCostTotal)
		
		//Calculate Actual Labor and Actual Expense
		this.actualLaborTotal  = this.getAllAcutalLaborTotal()
		this.actualExpenseTotal = this.getHHExpenseMonetaryAmountTotal() ?:0.00
		
		this.actualStdOHCostTotal = getAllActualStdOHCostTotal()
		this.actualTotal  = this?.actualLaborTotal.add(this?.actualExpenseTotal).add(this.actualStdOHCostTotal)
		
		//Calculate Variance Labor and Variance Expense
		this.varianceLaborTotal = this?.actualLaborTotal -this?.plannedLaborTotal
		this.varianceExpenseTotal = this?.actualExpenseTotal - this?.plannedExpenseTotal
		this.varianceOHCostTotal = this?.actualStdOHCostTotal - this?.plannedStdOHCostTotal
		this.varianceTotal = this?.actualTotal -this?.plannedTotal

		//Calculate Hours
		this.plannedHoursTotal = getPlannedHoursTotal()
		this.actualHoursTotal = getActualHoursTotal()
		this.varianceHoursTotal = this?.actualHoursTotal.minus(this?.plannedHoursTotal)
		
		return ""
	}
	
	@Transient
	def getAllPlannedLaborTotal() {		
		BigDecimal plannedLaborTotal = 0.00
		if(this?.plannedLabor?.laborDetails) {
			this?.plannedLabor?.laborDetails?.each { projectLaborDetail->					
				if(projectLaborDetail?.standardCost) {
					plannedLaborTotal = plannedLaborTotal.add(projectLaborDetail?.standardCost)
				}
			}
		}
		return plannedLaborTotal
	}
	
	@Transient
	def getAllPlannedExpenseTotal() {	
		BigDecimal plannedExpenseTotal = 0.00
		if(this?.plannedExpense?.expenseDetails) {
			this?.plannedExpense?.expenseDetails?.each { plannedExpense ->
				if(plannedExpense?.totalCost){
					plannedExpenseTotal = plannedExpenseTotal.add(plannedExpense?.totalCost)
				}
			}
		}
		return plannedExpenseTotal
	}
	@Transient
	def getAllPlannedStdOHCostTotal() {		
		BigDecimal plannedOHCost = 0.00
		if(this?.plannedLabor?.laborDetails) {
			this?.plannedLabor?.laborDetails?.each { projectLaborDetail->
				if(projectLaborDetail?.overHeadCost) {
					plannedOHCost = plannedOHCost.add(projectLaborDetail?.overHeadCost)
				}
			}	
		}
		return plannedOHCost
	}
	
	@Transient
	def getAllAcutalLaborTotal() {	
		BigDecimal actualLaborTotal = 0.00		
		if(this?.actualLabor?.laborDetails) {
			this?.actualLabor?.laborDetails?.each { projectActualDetail->
				if(projectActualDetail?.standardTotalCost) {
					actualLaborTotal = actualLaborTotal.add(projectActualDetail?.standardTotalCost)
				}
			}		
		}
		return actualLaborTotal
	}
	
	@Transient
	def getHHExpenseMonetaryAmountTotal() {		
		BigDecimal actualExpenseTotal = 0.00		
		if(this?.actualExpense?.actualExpenseDetails) {
			this?.actualExpense?.actualExpenseDetails?.each { actExpense ->					
				if(actExpense?.monetaryAmount) {
					actualExpenseTotal = actualExpenseTotal.add(actExpense?.monetaryAmount)
				}				
			}
		}
		return actualExpenseTotal
	}
	
	@Transient
	def getAllActualStdOHCostTotal() {		
		BigDecimal actualOHCost = 0.00
		if(this?.actualLabor?.laborDetails) {
			this?.actualLabor?.laborDetails?.each { projectActualDetail->
				if(projectActualDetail?.standardOverheadCost) {
					actualOHCost = actualOHCost.add(projectActualDetail?.standardOverheadCost)
				}
			}		
		}
		return actualOHCost
	}
	
	@Transient
	def getPlannedHoursTotal() {
		
		BigDecimal plannedHours = 0.00
			
		if(this.plannedLabor) {
					
			this?.plannedLabor?.laborDetails?.each { projectLaborDetail->
						
				if(projectLaborDetail?.hours) {
					plannedHours = plannedHours.add(projectLaborDetail?.hours)
				}
			}	
		}
		return plannedHours
	}
	
	@Transient
	def getActualHoursTotal() {
		
		BigDecimal actualHours = 0.00				
		if(this?.actualLabor) {
					
			this?.actualLabor?.laborDetails?.each { projectActualDetail->
						
				if(projectActualDetail?.hours) {
					actualHours = actualHours.add(projectActualDetail?.hours)
				}
			}
		}
		return actualHours
	}
	
	@Transient
	def getPlannedBillAmountTotal() {
		
		BigDecimal billAmtTotal = 0.00
			
		if(this.plannedLabor) {
					
			this?.plannedLabor?.laborDetails?.each { projectLaborDetail->
						
				if(projectLaborDetail?.billAmountTotal) {
					billAmtTotal = billAmtTotal.add(projectLaborDetail?.billAmountTotal)
				}
			}
		}
		return billAmtTotal
	}
	
	
	ProjectServiceDetail(defaultService) {
		this.service = defaultService
	}  
}
