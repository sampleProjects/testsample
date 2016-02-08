package org.solcorp.etech

class Expense extends Product {
	String glAccountNumber
	
	static mapping = {
		discriminator value: ProductClassType.EXPENSES
	}
	
	def beforeInsert = {
		def auditableBeforeInsert = super.beforeInsert.clone()
		auditableBeforeInsert.delegate = delegate
		auditableBeforeInsert.call()
		
		setDefaults()
	}
	
	def beforeUpdate = {
		def auditableBeforeUpdate = super.beforeUpdate.clone()
		auditableBeforeUpdate.delegate = delegate
		auditableBeforeUpdate.call()
		
		setDefaults()
	}
	
	private def setDefaults = {
		if(! standardRate) {
			standardRate = 0.00
		}
	}
	
	static constraints = {
		glAccountNumber(nullable: true, maxSize: Constants.PRODUCT_EXPENSE_CATEGORY_GLACCOUNT_LENGTH, scale : 0)
	}
}
