package org.solcorp.etech

class Miscellaneous extends Product {

	static mapping = {
		discriminator value: ProductClassType.MISCELLANEOUS
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
}
