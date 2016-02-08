package org.solcorp.etech

import java.util.Date;

class LaborActivityGroup extends Auditable {

	/**
	 * Used as primary key in all modules.
	 */
	Long id
	
	/**
	 * OperationGroup
	 */
    String code
	
	/**
	 * Description
	 */
	String description
		
	static belongsTo = [coe: COE]
	
	static hasMany = [laborActivityCodes: LaborActivityCode, services: Service]
	
	static constraints = {
		code (nullable : false, blank : false, unique : true, maxSize : Constants.LABORACTIVITYGROUP_CODE_LENGTH)
		description (nullable : true, maxSize : Constants.LABORACTIVITYGROUP_DESCRIPTION_LENGTH)
		dateCreated (nullable : true, display : false)
		lastUpdated (nullable : true, display : false)		
		laborActivityCodes (nullable : true, display : false)
		services (nullable : true, display : false)
    }
	
	static mapping = {
		//id generator : 'uuid'
		laborActivityCodes cascade : 'save-update'
		services cascade : 'save-update'
	}
}
