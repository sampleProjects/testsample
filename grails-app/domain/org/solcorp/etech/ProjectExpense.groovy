package org.solcorp.etech

import java.util.Date;

import org.grails.databinding.BindingFormat;

class ProjectExpense extends Auditable {

	Long id
		
	@BindingFormat("MM/dd/yyyy")
	Date expenseDate
	
	List expenseDetails = new ArrayList()
	
	static hasMany = [expenseDetails: ProjectExpenseDetail]
	
    static constraints = {
		expenseDate(nullable: false, blank: false)
    }
	
	static mapping = {
		expenseDetails cascade: 'all-delete-orphan'
	 }
}
