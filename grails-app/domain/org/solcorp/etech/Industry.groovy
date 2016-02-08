package org.solcorp.etech

class Industry extends Auditable {
	
	Long id
	
	String code
	
	String name
	
	String description
	
    static constraints = {
		code (nullable: false, blank: false, unique: true, maxSize: Constants.INDUSTRY_CODE_LENGTH)
		name(nullable: true, maxSize: Constants.INDUSTRY_NAME_LENGTH)
		description(nullable: true, maxSize: Constants.INDUSTRY_DESCRIPTION_LENGTH)
    }
}
