package org.solcorp.etech

class COE extends Auditable {

	Long id
	
	String code
	
	String description
	
	static hasMany = [laborActivityGroup: LaborActivityGroup] 
	
	static constraints = {
		code(nullable: false, blank: false, unique: true, maxSize: Constants.COE_CODE_LENGTH)
		description(nullable: true, maxSize: Constants.COE_DESCRIPTION_LENGTH)
		dateCreated(nullable: true, display: false)
		lastUpdated(nullable: true, display: false)
	}

	static mapping = { 
		laborActivityGroup cascade: 'save-update'
	}
}
