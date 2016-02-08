package org.solcorp.etech
/**
 * This class is used to store user information
 * @author hjjogiya
 *
 */
class Service extends Product {
	
	//static hasMany = [projects: Project]
	
	static belongsTo = Project 
		
	LaborActivityGroup laborActivityGroup
	
	Product product

	static constraints = {
		dateCreated(nullable: true, display: false)
		lastUpdated(nullable: true, display: false)
		laborActivityGroup nullable: true
		product nullable: true
    }
	
	static mapping = {
		discriminator value: ProductClassType.SERVICES
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
