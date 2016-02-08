package org.solcorp.etech

import java.util.Date;

import org.grails.databinding.BindingFormat;

class ProjectActualExpense extends Auditable {

	Long id
			
	List actualExpenseDetails = new ArrayList()
	
	static hasMany = [actualExpenseDetails: ProjectActualExpenseDetail]
	
    static constraints = {
		
    }
}
