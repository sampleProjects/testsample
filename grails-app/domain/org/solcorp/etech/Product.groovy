package org.solcorp.etech

import javax.persistence.Transient

/**
 * This class is used to store user information
 * @author hjjogiya
 *
 */
class Product extends Auditable {
	
	Long id	
    
	String code
    
	@Transient
	ProductClassType productClassType
	
	String description
	
	ProductCategory productCategory
	
	StatusType status
	
	BigDecimal standardRate
	
	static hasMany = [services: Service]
	
    static constraints = {
		code(nullable: false, blank: false, unique: true, maxSize: Constants.PRODUCT_CODE_LENGTH)
		productClassType(nullable: true)
		description(nullable: true, blank: true, maxSize: Constants.PRODUCT_DESCRIPTION_LENGTH)
		productCategory(nullable: true, blank: true, maxSize: Constants.PRODUCT_CATEGORY_LENGTH)
		
		status(nullable: true, display: false)
		services(nullable: true, display: false)
		dateCreated(nullable: true, display: false)
		lastUpdated(nullable: true, display: false)
		standardRate(nullable: true)
    }
	
	static transients = ['productClassType']
	
	@Transient
	public getProductClassType() {
		
		ProductClassType productClassType 
		
		if(this instanceof ProjectProduct) {
			productClassType = ProductClassType.PROJECT
		} else if (this instanceof Service) {
			productClassType = ProductClassType.SERVICES
		} else if (this instanceof Expense) {
			productClassType = ProductClassType.EXPENSES
		} else if (this instanceof Miscellaneous) {
			productClassType = ProductClassType.MISCELLANEOUS
		}
	}
	
	def beforeInsert = {
		def auditableBeforeInsert = super.beforeInsert.clone()
		auditableBeforeInsert.delegate = delegate
		auditableBeforeInsert.call()
	}
	
	def beforeUpdate = {
		def auditableBeforeUpdate = super.beforeUpdate.clone()
		auditableBeforeUpdate.delegate = delegate
		auditableBeforeUpdate.call()
	}
}
