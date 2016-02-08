package org.solcorp.etech

class ProjectCategory extends Auditable {
	
	/**
	 * Use as id 
	 */
	Long id
	
	/**
	 * store to category 
	 */
	String category
	
	/**
	 * store to description 
	 */
	String description
	
	static constraints = {
		category(nullable: false, blank: false, unique: true, maxSize: Constants.PROJECT_CATEGORY_LENGTH )
		description(nullable: true, maxSize: Constants.PROJECT_CATEGORY_DESCRIPTION_LENGTH )
	}

	static mapping = { 
		
	}
}
