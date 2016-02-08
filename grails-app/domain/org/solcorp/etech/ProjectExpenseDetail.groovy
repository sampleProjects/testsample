package org.solcorp.etech

class ProjectExpenseDetail {
	
	Long id
	
	Expense  expenseCode
	
	BigDecimal qty
	
	BigDecimal unitCost
	
	BigDecimal totalCost
	
	Boolean isFromActualExpense = false
	
	static belongsTo = [projectExpense: ProjectExpense]
	
    static constraints = {
		expenseCode(nullable: true)
		qty(nullable: false, blank: false)
		unitCost(nullable: false, blank: false)	
		projectExpense(nullable : true)
		isFromActualExpense(nullable : true)
    }
}
