package org.solcorp.etech

import javax.persistence.Transient;

class ProductCategory extends Auditable {

	Long id
	
	String category
	
	String description
	
	@Transient
	ProductClassType productClassType
	
    static constraints = {
		category(nullable: false, blank: false, unique: true, maxSize: Constants.PRODUCTCATEGORY_CATEGORY_LENGTH)
		description(nullable: true, blank: true, maxSize: Constants.PRODUCTCATEGORY_DESCRIPTION_LENGTH)
		productClassType(nullable: true)
    }
	
	static transients = ['productClassType']
	
	@Transient
	public getProductClassType() {
		
		ProductClassType productClassType
		
		if(this instanceof ProjectProductCategory) {
			productClassType = ProductClassType.PROJECT
		} else if (this instanceof ServiceCategory) {
			productClassType = ProductClassType.SERVICES
		} else if (this instanceof ExpenseCategory) {
			productClassType = ProductClassType.EXPENSES
		} else if (this instanceof MiscellaneousCategory) {
			productClassType = ProductClassType.MISCELLANEOUS
		}
	}
}
