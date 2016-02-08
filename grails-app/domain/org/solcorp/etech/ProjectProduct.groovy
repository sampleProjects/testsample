package org.solcorp.etech
/**
 * This class is used to store user information
 * @author hjjogiya
 *
 */
class ProjectProduct extends Product {

	static mapping = {
        discriminator value: ProductClassType.PROJECT
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
