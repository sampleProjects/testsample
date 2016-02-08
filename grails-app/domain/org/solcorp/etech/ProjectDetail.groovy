package org.solcorp.etech

import javax.persistence.Transient;

import org.grails.databinding.BindingFormat;

class ProjectDetail   {
	
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
	
	/*ProjectLabor actualLabor*/
	
	/*	
	ProjectExpense actualExpense*/
	
	static belongsTo = [project: Project ]
		
	ProjectProduct projectProduct
	
	@Transient
	int index
	
	static transients = ['index']
		
    static constraints = {
		projectProduct(nullable: false)
		scheduleStartDate(nullable: true, blank: true)
		scheduleCompDate(nullable: true, blank: true)		
		reviseStartDate(nullable: true, blank: true)		
		reviseCompDate(nullable: true, blank: true)		
		actualStartDate(nullable: true, blank: true)		
		actualCompDate(nullable: true, blank: true)
		service(nullable: false)
		plannedLabor(nullable: true)
		plannedExpense(nullable: true)
    }
	
	@Transient
	def getServicePlannedTotal() {		
	
		BigDecimal PlannedTotal = 0.00
		
		BigDecimal laborTotal = plannedLabor?.projectLaborTotal ?: 0.00 
		BigDecimal expenseTotal = plannedExpense?.projectExpenseTotal ?: 0.00
		
		PlannedTotal = laborTotal + expenseTotal 
		
		return PlannedTotal
	}
	
}
